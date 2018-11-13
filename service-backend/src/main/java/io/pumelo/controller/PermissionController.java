package io.pumelo.controller;

import io.pumelo.authorizion.AuthFilter;
import io.pumelo.common.basebean.PermissionType;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.backend.vo.permission.MenuVo;
import io.pumelo.data.backend.vo.permission.PermissionVo;
import io.pumelo.permission.annotation.PermissionFilter;
import io.pumelo.permission.annotation.PermissionRegister;
import io.pumelo.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: pumelo
 * 2018/3/22
 */
@RestController
@Api(description = "权限相关")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "get_all_permission",parentKey = "permission_manage", name = "获取所有权限",mappingUrl = "/all_permission",type = PermissionType.FUNCTION)
    @ApiOperation(value = "获取所有权限")
    @GetMapping("/all_permission")
    public ApiResponse<PermissionVo> getAllPermission(){
        return permissionService.getAllPermission();
    }

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "get_permission",parentKey = "permission_manage", name = "获取登录用户的权限",mappingUrl = "/permission",type = PermissionType.FUNCTION)
    @ApiOperation(value = "获取登录用户的权限")
    @GetMapping("/permission")
    public ApiResponse<PermissionVo> getPermission(){
        return permissionService.getPermission();
    }

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "get_menu",parentKey = "permission_manage", name = "获取登录用户的菜单",mappingUrl = "/menu",type = PermissionType.FUNCTION)
    @ApiOperation(value = "获取登录用户的菜单")
    @GetMapping("/menu")
    public ApiResponse<MenuVo> getMenu(){
        return permissionService.getMenu();
    }

}
