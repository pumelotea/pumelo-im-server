package io.pumelo.service;

import com.alibaba.fastjson.JSON;
import io.pumelo.common.errorcode.Syscode;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.backend.entity.RoleEntity;
import io.pumelo.data.backend.entity.BackendUserEntity;
import io.pumelo.data.backend.entity.UserRoleEntity;
import io.pumelo.data.backend.repo.RoleEntityRepo;
import io.pumelo.data.backend.repo.BackendUserEntityRepo;
import io.pumelo.data.backend.repo.UserRoleEntityRepo;
import io.pumelo.data.backend.struct.SessionUser;
import io.pumelo.data.backend.vo.AccessTokenVo;
import io.pumelo.data.backend.vo.LoginVo;
import io.pumelo.data.backend.vo.user.UserEnableVo;
import io.pumelo.data.backend.vo.user.UserVo;
import io.pumelo.redis.ObjectRedis;
import io.pumelo.utils.BeanUtils;
import io.pumelo.utils.jwt.JwtConstant;
import io.pumelo.utils.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * author: pumelo
 * 2018/3/21
 */
@Service
public class UserService {

    @Autowired
    private AuthService authService;
    @Autowired
    private BackendUserEntityRepo backendUserEntityRepo;
    @Autowired
    private RoleEntityRepo roleEntityRepo;
    @Autowired
    private UserRoleEntityRepo userRoleEntityRepo;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private ObjectRedis redis;
    @Autowired
    private PermissionService permissionService;


    private boolean isExistUsername(String username) {
        return backendUserEntityRepo.findByUsernameAndIsTrashFalse(username) != null;
    }

    private boolean isExistRole(String roleId) {
        return roleEntityRepo.findByRoleIdAndIsTrashFalse(roleId) != null;
    }

    /**
     * 用户和角色合并为一个vo
     * @param newUser
     * @param roleId
     * @return
     */
    private UserVo getUserVo(BackendUserEntity newUser, String roleId) {
        RoleEntity roleEntity = roleEntityRepo.findByRoleIdAndIsTrashFalse(roleId);
        UserVo userVo = BeanUtils.copyAttrs(new UserVo(),newUser);
        userVo = BeanUtils.copyAttrs(userVo,roleEntity);
        return userVo;
    }
    /**
     * 创建用户并且指定一个角色
     * @param username
     * @param password
     * @param roleId
     * @return
     */
    @Transactional
    public ApiResponse<UserVo> createUser(String username, String password, String roleId){
        if (isExistUsername(username)){
            return ApiResponse.prompt(Syscode.ACCOUNT_EXISTS);
        }
        if (!isExistRole(roleId)){
            return ApiResponse.prompt(Syscode.ROLE_NOT_EXISTS);
        }
        BackendUserEntity newUser = new BackendUserEntity(username,password);
        newUser = backendUserEntityRepo.save(newUser);
        UserRoleEntity newUserRoleEntity = new UserRoleEntity(newUser.getUserId(),roleId);
        userRoleEntityRepo.save(newUserRoleEntity);
        return ApiResponse.ok(getUserVo(newUser,roleId));
    }

    /**
     * 修改密码
     * @param userId
     * @param password
     * @return
     */
    @Transactional
    public ApiResponse resetUserPassword(String userId, String password){
        BackendUserEntity backendUserEntity = backendUserEntityRepo.findByUserIdAndIsTrashFalse(userId);
        if (backendUserEntity == null){
            return ApiResponse.prompt(Syscode.ACCOUNT_NOT_EXISTS);
        }
        backendUserEntity.resetPassword(password);
        backendUserEntityRepo.save(backendUserEntity);
        //一旦修改了账号的密码，应当立即把该用户踢掉
        redis.delete("user/"+ backendUserEntity.getUserId());
        return ApiResponse.ok(Syscode.SC_OK);
    }

    /**
     * 用户启用禁用
     * @param userId
     * @return
     */
    @Transactional
    public ApiResponse<UserEnableVo> updateUserEnable(String userId){
        BackendUserEntity backendUserEntity = backendUserEntityRepo.findByUserIdAndIsTrashFalse(userId);
        if (backendUserEntity == null){
            return ApiResponse.prompt(Syscode.ACCOUNT_NOT_EXISTS);
        }
        if (authService.getId().equals(userId)){
            return ApiResponse.prompt(Syscode.CAN_NOT_UPDATE_PERSONAL_ENABLE);
        }
        backendUserEntity.updateEnable();
        backendUserEntityRepo.save(backendUserEntity);
        //立即T掉用户
        redis.delete("user/"+userId);
        return ApiResponse.ok(BeanUtils.copyAttrs(new UserEnableVo(), backendUserEntity));
    }

    /**
     * 修改用的角色
     * @param userId
     * @param roleId
     * @return
     */
    @Transactional
    public ApiResponse<UserVo> updateUserRole(String userId, String roleId){
        BackendUserEntity backendUserEntity = backendUserEntityRepo.findByUserIdAndIsTrashFalse(userId);
        if (backendUserEntity == null){
            return ApiResponse.prompt(Syscode.ACCOUNT_NOT_EXISTS);
        }
        RoleEntity roleEntity = roleEntityRepo.findByRoleIdAndIsTrashFalse(roleId);
        if (roleEntity == null){
            return ApiResponse.prompt(Syscode.ROLE_NOT_EXISTS);
        }
        if(backendUserEntity.getUsername().equals("root")){
            return ApiResponse.prompt(Syscode.SUPERROLE_NOT_PERMIT_CHANGE);
        }
        if(authService.getId().equals(userId)){
            return ApiResponse.prompt(Syscode.CAN_NOT_UPDATE_SELF_USER_ROLE);
        }
        //删除之前的绑定关系
        List<UserRoleEntity> userRoleEntities = userRoleEntityRepo.findByUserIdAndIsTrashFalse(userId);
        userRoleEntities.stream()
                .filter(userRoleEntity -> !userRoleEntity.getTrash())
                .forEach(userRoleEntity -> {
                    userRoleEntity.setTrash(true);
                    userRoleEntityRepo.save(userRoleEntity);
                });
        //增加新的绑定关系
        UserRoleEntity newUserRoleEntity = new UserRoleEntity(userId,roleId);
        userRoleEntityRepo.save(newUserRoleEntity);
        return ApiResponse.ok(getUserVo(backendUserEntity,roleId));
    }

    /**
     * 登录
     * 只缓存用户信息
     * @param username
     * @param password
     * @return
     */
    public ApiResponse<LoginVo> login(String username, String password){
        BackendUserEntity backendUserEntity = backendUserEntityRepo.findByUsernameAndIsTrashFalse(username);
        if(null == backendUserEntity) {
            return ApiResponse.prompt(Syscode.ACCOUNT_NOT_EXISTS);
        }
        if(!backendUserEntity.getEnable()){
            return ApiResponse.prompt(Syscode.ACCOUNT_DISABLE);
        }
        if (!backendUserEntity.isAuthentication(password)){//密码输入有误
            return ApiResponse.prompt(Syscode.ACCOUNT_PWD_ERROR);
        }
        try {
            long nowMillis = System.currentTimeMillis();
            String accessToken = JwtUtils.createJWT(JwtConstant.JWT_SECRET,nowMillis,JwtConstant.JWT_ID, backendUserEntity.getUserId() , JwtConstant.JWT_TTL);
            long expiresAt = System.currentTimeMillis()+JwtConstant.JWT_TTL;
            //封装缓存信息,包含权限
            AccessTokenVo accessTokenVo = new AccessTokenVo(accessToken,"bearer",expiresAt/1000);
            LoginVo loginVo = BeanUtils.copyAttrs(new LoginVo(accessTokenVo), backendUserEntity);
            SessionUser<LoginVo> sessionUser = new SessionUser<>();
            sessionUser.setUser(loginVo);
            sessionUser.setPermission(permissionService.getPermissionByUserId( backendUserEntity.getUserId()));
            String sessionUserJson = JSON.toJSONString(sessionUser);
            redisTemplate.opsForValue().set("user/"+ backendUserEntity.getUserId(), sessionUserJson, JwtConstant.JWT_TTL, TimeUnit.MILLISECONDS);
            return ApiResponse.ok(loginVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.prompt(Syscode.JWT_TOKEN_INVALID);
        }
    }

    public ApiResponse logout(){
        String userId = authService.getId();
        redis.delete("user/"+userId);
        return ApiResponse.prompt(Syscode.SC_OK);
    }

    /**
     * 获取用户vo
     * @param userId
     * @return
     */
    @Transactional
    public ApiResponse<UserVo> getUser(String userId){
        BackendUserEntity backendUserEntity = backendUserEntityRepo.findByUserIdAndIsTrashFalse(userId);
        if (backendUserEntity == null){
            return ApiResponse.prompt(Syscode.USER_NOT_EXIST);
        }
        List<String> roleIds = userRoleEntityRepo.findRoleIdsByUserId(userId);
        if (roleIds.size() == 0){
            return ApiResponse.prompt(Syscode.USER_ROLE_NOT_FOUND);
        }
        String roleId = roleIds.get(0);
        return ApiResponse.ok(getUserVo(backendUserEntity,roleId));
    }

    /**
     * 获取用户列表
     * @param key
     * @param page
     * @param size
     * @return
     */
    public ApiResponse<Page<UserVo>> getUserPage(String key, Integer page, Integer size){
        Pageable pageable = PageRequest.of(page,size);
        return ApiResponse.ok(getUserVos(pageable));
    }

    /**
     * 对Page增强处理
     * @param pageable
     * @return
     */
    private Page<UserVo> getUserVos(Pageable pageable) {
        String uid = authService.getId();
        Page<BackendUserEntity> userEntities = backendUserEntityRepo.findByPage(uid,pageable);
        List<UserVo> userVos = new ArrayList<>();
        userEntities.forEach(backendUserEntity -> {
            List<String> roleIds = userRoleEntityRepo.findRoleIdsByUserId(backendUserEntity.getUserId());
            UserVo userVo = new UserVo("暂无","暂无");
            if(roleIds!=null && roleIds.size()>0){
                RoleEntity roleEntity = roleEntityRepo.findByRoleIdAndIsTrashFalse(roleIds.get(0));
                if(roleEntity!=null){
                    userVo.setRoleId(roleEntity.getRoleId());
                    userVo.setRoleName(roleEntity.getRoleName());
                }
                userVo = BeanUtils.copyAttrs(userVo, backendUserEntity);
            }
            userVos.add(userVo);
        });
        return new PageImpl<>(userVos, pageable, userEntities.getTotalElements());
    }

    public ApiResponse bindPhone(){
        return null;
    }

    public ApiResponse bindEmail(){
        return null;
    }
}
