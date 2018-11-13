package io.pumelo.controller;

import io.pumelo.common.basebean.PermissionType;
import io.pumelo.permission.annotation.PermissionRegister;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * author: pumelo
 * 2018/3/23
 */
@RestController
@ApiIgnore
public class MenuController {

    @PermissionRegister(key = "user_manage",name = "用户管理",parentKey = "user",type = PermissionType.MENU,mappingUrl = "/user")
    @GetMapping("_/menu/user_manage")
    public void userManage(){

    }

    @PermissionRegister(key = "role_manage",name = "角色管理",parentKey = "user",type = PermissionType.MENU,mappingUrl = "/role")
    @GetMapping("_/menu/role_manage")
    public void roleManage(){

    }

    @PermissionRegister(key = "permission_manage",name = "权限视图",parentKey = "user",type = PermissionType.MENU,mappingUrl = "/permission")
    @GetMapping("_/menu/permission_manage")
    public void permissionManage(){

    }

    @PermissionRegister(key = "main_dashboard",name = "主面板视图",parentKey = "dashboard",type = PermissionType.MENU,mappingUrl = "/dashboard")
    @GetMapping("_/menu/main")
    public void main(){

    }
}
