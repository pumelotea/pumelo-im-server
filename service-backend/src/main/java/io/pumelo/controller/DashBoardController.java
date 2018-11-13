package io.pumelo.controller;

import io.pumelo.authorizion.AuthFilter;
import io.pumelo.common.basebean.PermissionType;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.permission.annotation.PermissionFilter;
import io.pumelo.permission.annotation.PermissionRegister;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DashBoard
 * author: pumelo
 * 2018/4/2
 */
@RestController
public class DashBoardController {

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "get_user_number",parentKey = "main_dashboard", name = "获取用户数",mappingUrl = "/dashboard/user_num",type = PermissionType.FUNCTION)
    @GetMapping("/dashboard/user_num")
    public ApiResponse getUserNumber(){
        return null;
    }


    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "get_online_user",parentKey = "main_dashboard", name = "获取登录用户数",mappingUrl = "/dashboard/user_cached_num",type = PermissionType.FUNCTION)
    @GetMapping("/dashboard/user_cached_num")
    public ApiResponse getOnlineUser(){
        return null;
    }

}
