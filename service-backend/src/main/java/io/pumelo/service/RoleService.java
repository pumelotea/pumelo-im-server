package io.pumelo.service;

import io.pumelo.common.errorcode.Syscode;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.backend.dto.role.RoleDto;
import io.pumelo.data.backend.dto.role.RolePermissionDto;
import io.pumelo.data.backend.entity.RoleEntity;
import io.pumelo.data.backend.entity.RolePermissionEntity;
import io.pumelo.data.backend.entity.UserRoleEntity;
import io.pumelo.data.backend.repo.RoleEntityRepo;
import io.pumelo.data.backend.repo.RolePermissionEntityRepo;
import io.pumelo.data.backend.repo.UserRoleEntityRepo;
import io.pumelo.data.backend.struct.PermissionUnit;
import io.pumelo.data.backend.vo.role.RoleInfoVo;
import io.pumelo.data.backend.vo.role.RoleVo;
import io.pumelo.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * 角色服务
 * author: pumelo
 * 2018/3/21
 */
@Service
public class RoleService {

    @Autowired
    private RoleEntityRepo roleEntityRepo;

    @Autowired
    private RolePermissionEntityRepo rolePermissionEntityRepo;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserRoleEntityRepo userRoleEntityRepo;

    @Autowired
    private AuthService authService;


    /**
     * 创建角色
     * @param roleDto
     * @return
     */
    public ApiResponse<RoleInfoVo> createRole(RoleDto roleDto) {
        RoleEntity newRole = BeanUtils.copyAttrs(new RoleEntity(),roleDto);
        newRole = roleEntityRepo.save(newRole);
        return ApiResponse.ok(BeanUtils.copyAttrs(new RoleInfoVo(),newRole));
    }

    /**
     * 更新角色
     * 不参数对权限的修改
     * @param roleId
     * @param roleDto
     * @return
     */
    @Transactional
    public ApiResponse<RoleInfoVo> updateRole(String roleId,RoleDto roleDto) {
        RoleEntity roleEntity = roleEntityRepo.findByRoleIdAndIsTrashFalse(roleId);
        if(roleEntity == null){
            return ApiResponse.prompt(Syscode.ROLE_NOT_EXISTS);
        }
        if(roleEntity.isSystemRole()){
            return ApiResponse.prompt(Syscode.ADMIN_CAN_NOT_UPDATE);
        }
        roleEntity = BeanUtils.copyAttrs(roleEntity,roleDto);
        roleEntity = roleEntityRepo.save(roleEntity);
        return ApiResponse.ok(BeanUtils.copyAttrs(new RoleInfoVo(),roleEntity));
    }


    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @Transactional
    public ApiResponse deleteRole(String roleId) {
        RoleEntity roleEntity = roleEntityRepo.findByRoleIdAndIsTrashFalse(roleId);
        if(roleEntity == null){
            return ApiResponse.prompt(Syscode.ROLE_NOT_EXISTS);
        }
        if(roleEntity.isSystemRole()){
            return ApiResponse.prompt(Syscode.ADMIN_CAN_NOT_DELETE);
        }
        //不能删除在使用的角色
        List<String> userIds = userRoleEntityRepo.findUserIdsByRoleId(roleId);
        if(userIds != null && userIds.size()  > 0){
            return ApiResponse.prompt(Syscode.ROLE_USING);
        }
        roleEntity.delete();
        roleEntityRepo.save(roleEntity);
        return ApiResponse.prompt(Syscode.SC_OK);
    }


    /**
     * 查看角色信息
     * 不包含权限信息
     * @param roleId
     * @return
     */
    public ApiResponse<RoleInfoVo> getRole(String roleId){
        RoleEntity roleEntity = roleEntityRepo.findByRoleIdAndIsTrashFalse(roleId);
        if(roleEntity == null){
            return ApiResponse.prompt(Syscode.ROLE_NOT_EXISTS);
        }
        return ApiResponse.ok(BeanUtils.copyAttrs(new RoleInfoVo(),roleEntity));
    }

    /**
     * 查看角色信息
     * 含权限信息
     * @param roleId
     * @return
     */
    @Transactional
    public ApiResponse<RoleVo> getRolePermission(String roleId){
        RoleEntity roleEntity = roleEntityRepo.findByRoleIdAndIsTrashFalse(roleId);
        if(roleEntity == null){
            return ApiResponse.prompt(Syscode.ROLE_NOT_EXISTS);
        }
        List<PermissionUnit> permission = permissionService.getPermission(roleId);
        return ApiResponse.ok(BeanUtils.copyAttrs(new RoleVo(permission),roleEntity));
    }

    /**
     * 获取角色列表
     * 带分页功能
     * @param key
     * @param page
     * @param size
     * @return
     */
    public ApiResponse<Page<RoleInfoVo>> getRoleInfoPage(String key, Integer page, Integer size){
        Pageable pageable = PageRequest.of(page,size);
        return ApiResponse.ok(roleEntityRepo.findPage(pageable));
    }


    /**
     * 对角色赋予权限
     * @param rolePermissionDto
     * @return
     */
    @Transactional
    public ApiResponse assignPermission(RolePermissionDto rolePermissionDto) {
        String roleId = rolePermissionDto.getRoleId();
        RoleEntity roleEntity = roleEntityRepo.findByRoleIdAndIsTrashFalse(roleId);
        if(roleEntity == null){
            return ApiResponse.prompt(Syscode.ROLE_NOT_EXISTS);
        }
        //过滤自己的角色和系统创建的角色
        if(roleEntity.isSystemRole()){
            return ApiResponse.prompt(Syscode.ADMIN_CAN_NOT_UPDATE);
        }
        List<UserRoleEntity> list = userRoleEntityRepo.findByUserIdAndIsTrashFalse(authService.getId());
        if(null != list){
            for (UserRoleEntity entity:list) {
                if(entity.getRoleId().equals(roleId)){
                    return ApiResponse.prompt(Syscode.CAN_NOT_UPDATE_SELF_ROLE_PERMISSION);
                }
            }
        }
        //先删除所有权限
        rolePermissionEntityRepo.deleteByRoleId(roleId);
        //在角色-权限表中插入多条数据
        List<RolePermissionEntity> rolePermissions = new ArrayList<>();
        List<PermissionUnit> modules = rolePermissionDto.getPermission();
        if(modules == null){
            return ApiResponse.prompt(Syscode.SC_OK);
        }
        modules.forEach(module ->{
            RolePermissionEntity moduleEntity = new RolePermissionEntity(roleId,module.getPermissionKey());
            rolePermissions.add(moduleEntity);
            List<PermissionUnit> menus = module.getPermissionBranch();
            if(menus != null){
                menus.forEach(menu ->{
                    RolePermissionEntity menuEntity = new RolePermissionEntity(roleId,menu.getPermissionKey());
                    rolePermissions.add(menuEntity);
                    List<PermissionUnit> funcs = menu.getPermissionBranch();
                    if(funcs != null){
                        funcs.forEach(func -> {
                            RolePermissionEntity funcEntity = new RolePermissionEntity(roleId,func.getPermissionKey());
                            rolePermissions.add(funcEntity);
                        });
                    }
                });
            }
        });
        rolePermissionEntityRepo.saveAll(rolePermissions);
        return ApiResponse.prompt(Syscode.SC_OK);
    }

    public ApiResponse<List<RoleInfoVo>> getRoleInfoList(){
        return ApiResponse.ok(roleEntityRepo.findList());
    }

}
