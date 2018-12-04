package io.pumelo.im.service;

import io.pumelo.common.errorcode.IMCode;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.entity.UserEntity;
import io.pumelo.data.im.repo.UserEntityRepo;
import io.pumelo.data.im.vo.AccessTokenVo;
import io.pumelo.data.im.vo.user.UserSearchVo;
import io.pumelo.data.im.vo.user.UserVo;
import io.pumelo.im.router.Router;
import io.pumelo.redis.ObjectRedis;
import io.pumelo.utils.BeanUtils;
import io.pumelo.utils.EncryptionUtils;
import io.pumelo.utils.jwt.JwtConstant;
import io.pumelo.utils.jwt.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserEntityRepo userEntityRepo;

    @Autowired
    private AuthService authService;

    @Autowired
    private ObjectRedis objectRedis;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private Router router;

    /**
     * 登录
     *
     * @return
     */
    public ApiResponse<AccessTokenVo> login(String uid, String password) {
        UserEntity userEntity = userEntityRepo.findByUid(uid);
        if (null == userEntity) {
            return ApiResponse.prompt(IMCode.ACCOUNT_NOT_EXISTS);
        }
        if (!userEntity.isAuthentication(password)) {//密码输入有误
            return ApiResponse.prompt(IMCode.ACCOUNT_PWD_ERROR);
        }
        try {
            long nowMillis = System.currentTimeMillis();
            String accessToken = JwtUtils.createJWT(JwtConstant.JWT_SECRET, nowMillis, JwtConstant.JWT_ID, userEntity.getUid(), JwtConstant.JWT_TTL);
            long expiresAt = System.currentTimeMillis() + JwtConstant.JWT_TTL;
            AccessTokenVo accessTokenVo = new AccessTokenVo(accessToken, "bearer", expiresAt / 1000);
            accessTokenVo.setUid(uid);
            accessTokenVo.setUserVo(BeanUtils.copyAttrs(new UserVo(), userEntity));
            objectRedis.add("user/" + userEntity.getUid(), JwtConstant.JWT_TTL / 1000 / 60, accessTokenVo);
            return ApiResponse.ok(accessTokenVo);
        } catch (Exception e) {
            e.printStackTrace();
            //删除已经装在的资源
            objectRedis.delete("user/" + userEntity.getUid());
            return ApiResponse.prompt(IMCode.LOGIN_FAIL);
        }
    }


    /**
     * 注册
     *
     * @return
     */
    public ApiResponse<UserVo> register(String name, String password) {

        String id = getNewId();
        if (StringUtils.isBlank(id)) {
            return ApiResponse.prompt(IMCode.TOO_BUSY);
        }
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setSalt(UUID.randomUUID().toString());
            userEntity.setName(name);
            userEntity.setPassword(EncryptionUtils.sha1(password + userEntity.getSalt()));
            userEntity.setUid(id);
            userEntity = userEntityRepo.save(userEntity);
            return ApiResponse.ok(BeanUtils.copyAttrs(new UserVo(), userEntity));
        } catch (Exception e) {
            //返回资源
            redisTemplate.opsForSet().add("UserIdPool", id);
            return ApiResponse.prompt(IMCode.FAIL);
        }
    }

    private String getNewId() {
        return redisTemplate.opsForSet().pop("UserIdPool");
    }

    /**
     * 退出
     */
    public ApiResponse logout() {
        objectRedis.delete("/user" + authService.getId());
        router.removeUser(authService.getId());
        return ApiResponse.prompt(IMCode.SC_OK);
    }


    public ApiResponse<Page<UserSearchVo>> search(String keyword, int page, int size) {
        Page<UserEntity> listByKeyword = userEntityRepo.findListByKeyword("%" + keyword + "%", PageRequest.of(page, size));
        List<UserSearchVo> voList = new ArrayList<>();
        listByKeyword.getContent().forEach(userEntity -> {
            voList.add(BeanUtils.copyAttrs(new UserSearchVo(), userEntity));
        });
        return ApiResponse.ok(new PageImpl<>(voList, PageRequest.of(page, size), listByKeyword.getTotalElements()));
    }
}
