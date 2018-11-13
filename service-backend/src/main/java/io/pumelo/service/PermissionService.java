package io.pumelo.service;

import io.pumelo.common.basebean.PermissionType;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.backend.entity.PermissionEntity;
import io.pumelo.data.backend.entity.UserRoleEntity;
import io.pumelo.data.backend.repo.PermissionEntityRepo;
import io.pumelo.data.backend.repo.RolePermissionEntityRepo;
import io.pumelo.data.backend.repo.UserRoleEntityRepo;
import io.pumelo.data.backend.struct.PermissionUnit;
import io.pumelo.data.backend.vo.permission.MenuVo;
import io.pumelo.data.backend.vo.permission.PermissionVo;
import io.pumelo.permission.AbstractPermission;
import io.pumelo.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 权限服务
 * author: pumelo
 * 2018/3/22
 */
@Service
@Order(2)
public class PermissionService extends AbstractPermission {

    @Autowired
    private PermissionEntityRepo permissionEntityRepo;
    @Autowired
    private AuthService authService;
    @Autowired
    private RolePermissionEntityRepo rolePermissionEntityRepo;
    @Autowired
    private UserRoleEntityRepo userRoleEntityRepo;


    /**
     * 获取所有权限的树状结构
     * 主要用于选择权限
     * @return
     */
    @Transactional
    public ApiResponse<PermissionVo> getAllPermission(){
        List<PermissionEntity> allPermission = permissionEntityRepo.findByPermissionType(PermissionType.MODULE);
        List<PermissionUnit> moduleList = new ArrayList<>();
        //对MODULE层遍历
        allPermission.forEach(moduleEntity -> {
            //查询MENU层
            List<PermissionUnit> menuList = new ArrayList<>();
            List<PermissionEntity> menuPermission = permissionEntityRepo.findByPermissionType(PermissionType.MENU,moduleEntity.getPermissionKey());
            menuPermission.forEach(menuEntity -> {
                List<PermissionUnit> funcList = new ArrayList<>();
                //查询FUNCTION层
                List<PermissionEntity> funcPermission = permissionEntityRepo.findByPermissionType(PermissionType.FUNCTION,menuEntity.getPermissionKey());
                funcPermission.forEach( funcEntity->{
                    //处理func数据格式
                    PermissionUnit fun = BeanUtils.copyAttrs(new PermissionUnit(),funcEntity);
                    funcList.add(fun);
                });
                //关联Menu节点
                PermissionUnit menuUnit = BeanUtils.copyAttrs(new PermissionUnit(),menuEntity);
                menuUnit.setPermissionBranch(funcList);
                menuList.add(menuUnit);
            });
            //关联Module节点
            PermissionUnit moduleUnit = BeanUtils.copyAttrs(new PermissionUnit(),moduleEntity);
            moduleUnit.setPermissionBranch(menuList);
            moduleList.add(moduleUnit);
        });
        PermissionVo permissionVo = new PermissionVo(moduleList);
        return ApiResponse.ok(permissionVo);
    }

    /**
     * 获取用户权限的树状结构
     * 主要用于鉴权
     * @return
     */
    @Transactional
    public ApiResponse<PermissionVo> getPermission(){
        String userId = authService.getId();
        List<UserRoleEntity> roles = userRoleEntityRepo.findByUserIdAndIsTrashFalse(userId);
        if(roles == null || roles.size() == 0){
            return ApiResponse.ok(new PermissionVo(null));
        }
        String roleId = roles.get(0).getRoleId();
        PermissionVo permissionVo = new PermissionVo(getPermission(roleId));
        return ApiResponse.ok(permissionVo);
    }

    @Transactional
    public List<PermissionUnit> getPermissionByUserId(String userId){
        List<UserRoleEntity> roles = userRoleEntityRepo.findByUserIdAndIsTrashFalse(userId);
        if(roles == null || roles.size() == 0){
            return null;
        }
        String roleId = roles.get(0).getRoleId();
        return getPermission(roleId);
    }

    /**
     * 获取用户权限的树状结构
     * 主要用于鉴权
     * @return
     */
    @Transactional
    public List<PermissionUnit> getPermission(String roleId){
        List<String> permissionKeys = rolePermissionEntityRepo.findPermissionKeysByRoleId(roleId);
        if(permissionKeys == null || permissionKeys.size() == 0){
            return null;
        }
        List<PermissionEntity> allPermission = permissionEntityRepo.findByPermissionType(PermissionType.MODULE, permissionKeys);
        List<PermissionUnit> moduleList = new ArrayList<>();
        //对MODULE层遍历
        allPermission.forEach(moduleEntity -> {
            //查询MENU层
            List<PermissionUnit> menuList = new ArrayList<>();
            List<PermissionEntity> menuPermission = permissionEntityRepo.findByPermissionType(PermissionType.MENU,moduleEntity.getPermissionKey(),permissionKeys);
            menuPermission.forEach(menuEntity -> {
                List<PermissionUnit> funcList = new ArrayList<>();
                //查询FUNCTION层
                List<PermissionEntity> funcPermission = permissionEntityRepo.findByPermissionType(PermissionType.FUNCTION,menuEntity.getPermissionKey(),permissionKeys);
                funcPermission.forEach( funcEntity->{
                    //处理func数据格式
                    PermissionUnit fun = BeanUtils.copyAttrs(new PermissionUnit(),funcEntity);
                    funcList.add(fun);
                });
                //关联Menu节点
                PermissionUnit menuUnit = BeanUtils.copyAttrs(new PermissionUnit(),menuEntity);
                menuUnit.setPermissionBranch(funcList);
                menuList.add(menuUnit);
            });
            //关联Module节点
            PermissionUnit moduleUnit = BeanUtils.copyAttrs(new PermissionUnit(),moduleEntity);
            moduleUnit.setPermissionBranch(menuList);
            moduleList.add(moduleUnit);
        });
        return moduleList;
    }



    /**
     * 获取用户菜单
     * 主要用于UI显示
     * @return
     */
    @Transactional
    public ApiResponse<MenuVo> getMenu(){
        String userId = authService.getId();
        List<UserRoleEntity> roles = userRoleEntityRepo.findByUserIdAndIsTrashFalse(userId);
        if(roles == null || roles.size() == 0){
            return ApiResponse.ok(new MenuVo(null));
        }
        String roleId = roles.get(0).getRoleId();
        List<String> permissionKeys = rolePermissionEntityRepo.findPermissionKeysByRoleId(roleId);
        if(permissionKeys == null || permissionKeys.size() == 0){
            return ApiResponse.ok(new MenuVo(null));
        }
        List<PermissionEntity> allPermission = permissionEntityRepo.findByPermissionType(PermissionType.MODULE, permissionKeys);
        List<PermissionUnit> moduleList = new ArrayList<>();
        //对MODULE层遍历
        allPermission.forEach(moduleEntity -> {
            //查询MENU层
            List<PermissionUnit> menuList = new ArrayList<>();
            List<PermissionEntity> menuPermission = permissionEntityRepo.findByPermissionType(PermissionType.MENU,moduleEntity.getPermissionKey(),permissionKeys);
            menuPermission.forEach(menuEntity -> {
                //关联Menu节点
                PermissionUnit menuUnit = BeanUtils.copyAttrs(new PermissionUnit(),menuEntity);
                menuList.add(menuUnit);
            });
            //关联Module节点
            PermissionUnit moduleUnit = BeanUtils.copyAttrs(new PermissionUnit(),moduleEntity);
            moduleUnit.setPermissionBranch(menuList);
            moduleList.add(moduleUnit);
        });
        MenuVo menuVo = new MenuVo(moduleList);
        return ApiResponse.ok(menuVo);
    }

    @Override
    public boolean isTimeout() {
        return false;
    }

    /**
     * 通过覆盖本方法是否激活权限拦截
     * @return
     */
    @Override
    public boolean isActive(){
        return true;
    }
}
