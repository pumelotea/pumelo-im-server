package io.pumelo.controller;

import io.pumelo.authorizion.AuthFilter;
import io.pumelo.common.basebean.PermissionType;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.backend.vo.LoginVo;
import io.pumelo.data.backend.vo.role.RoleInfoVo;
import io.pumelo.data.backend.vo.user.UserEnableVo;
import io.pumelo.data.backend.vo.user.UserVo;
import io.pumelo.permission.annotation.PermissionFilter;
import io.pumelo.permission.annotation.PermissionRegister;
import io.pumelo.service.RoleService;
import io.pumelo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.util.List;

@RestController
@Validated
@Api(description = "账户相关")
public class AccountController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "create_user",parentKey = "user_manage", name = "创建用户",mappingUrl = "/user",type = PermissionType.FUNCTION)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",paramType = "form", dataType = "string",required = true),
            @ApiImplicitParam(name = "password",value = "密码",paramType = "form", dataType = "string",required = true),
            @ApiImplicitParam(name = "roleId",value = "角色Id",paramType = "form", dataType = "string",required = true)
    })
    @PostMapping("/user")
    public ApiResponse<UserVo> createUser(@NotBlank(message = "用户名不能为空") @Size(max = 36,message = "用户名最多36位") @RequestParam String username,
                                          @NotBlank(message = "密码不能为空") @Size(max = 36,message = "密码最多36位")  @RequestParam  String password,
                                          @NotBlank(message = "角色Id不能为空(角色不能为空)") @Size(min = 36,max = 36,message = "角色Id长度36位(角色不能为空)") @RequestParam  String roleId){
        return userService.createUser(username,password,roleId);
    }

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "reset_user_password",parentKey = "user_manage", name = "修改用户密码",mappingUrl = "/user/{userId}/password",type = PermissionType.FUNCTION)
    @PutMapping("/user/{userId}/password")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id",paramType = "path", dataType = "string",required = true),
            @ApiImplicitParam(name = "password",value = "新密码",paramType = "form", dataType = "string",required = true),
    })
    public ApiResponse resetUserPassword(@NotBlank(message = "用户Id不能为空") @Size(min = 36,max = 36,message = "用户Id长度36位(用户不能为空)") @PathVariable String userId,
                                         @NotBlank(message = "密码不能为空") @Size(max = 36,message = "密码最多36位")  @RequestParam String password){
        return userService.resetUserPassword(userId,password);
    }

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "update_user_enable",parentKey = "user_manage", name = "启用禁用用户",mappingUrl = "/user/{userId}/enable",type = PermissionType.FUNCTION)
    @PutMapping("/user/{userId}/enable")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id",paramType = "path", dataType = "string",required = true),
    })
    public ApiResponse<UserEnableVo> updateUserEnable(@NotBlank(message = "用户Id不能为空") @Size(min = 36,max = 36,message = "用户Id长度36位(用户不能为空)") @PathVariable String userId){
        return userService.updateUserEnable(userId);
    }

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "update_user_role",parentKey = "user_manage", name = "修改用户的角色",mappingUrl = "/user/{userId}/role",type = PermissionType.FUNCTION)
    @PutMapping("/user/{userId}/role")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id",paramType = "path", dataType = "string",required = true),
            @ApiImplicitParam(name = "roleId",value = "角色Id",paramType = "form", dataType = "string",required = true),
    })
    public ApiResponse<UserVo> updateUserRole(@NotBlank(message = "用户Id不能为空(用户不能为空)") @Size(min = 36,max = 36,message = "用户Id长度36位(用户不能为空)") @PathVariable String userId,
                                              @NotBlank(message = "角色Id不能为空(角色不能为空)") @Size(min = 36,max = 36,message = "角色Id长度36位(角色不能为空)") @RequestParam  String roleId){
        return userService.updateUserRole(userId,roleId);
    }

    @AuthFilter(skip = true)
    @PermissionFilter(skip = true)
    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",paramType = "form", dataType = "string",required = true),
            @ApiImplicitParam(name = "password",value = "密码",paramType = "form", dataType = "string",required = true),
    })
    public ApiResponse<LoginVo> login(@NotBlank(message = "用户名不能为空") @Size(max = 36,message = "用户名最多36位") @RequestParam String username,
                                      @NotBlank(message = "密码不能为空") @Size(max = 36,message = "密码最多36位")  @RequestParam String password){
        return userService.login(username,password);
    }

    @AuthFilter
    @PermissionFilter(skip = true)
    @PostMapping("/logout")
    public ApiResponse logout(){
        return userService.logout();
    }


    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "get_user",parentKey = "user_manage", name = "获取用户信息",mappingUrl = "/user/{userId}",type = PermissionType.FUNCTION)
    @GetMapping("/user/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id",paramType = "path", dataType = "string",required = true),
    })
    public ApiResponse<UserVo> getUser(@NotBlank(message = "用户Id不能为空") @Size(min = 36,max = 36,message = "用户Id长度36位(用户不能为空)") @PathVariable String userId){
        return userService.getUser(userId);
    }

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "get_user_page",parentKey = "user_manage", name = "获取用户分页",mappingUrl = "/users",type = PermissionType.FUNCTION)
    @GetMapping("/users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key",value = "搜索关键字",paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "page",value = "页码",paramType = "query", dataType = "int",required = true),
            @ApiImplicitParam(name = "size",value = "大小",paramType = "query", dataType = "int",required = true)
    })
    public ApiResponse<Page<UserVo>> getUserPage(String key, @Min(value = 0,message = "页码最小为0") @NotNull(message = "页码不能为空")  Integer page,
                                                 @Min(value = 1,message = "数据长度最小为1") @NotNull(message = "数据长度不能为空")  Integer size){
        return userService.getUserPage(key,page,size);
    }

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "get_role_info_list",parentKey = "user_manage", name = "获取角色下拉框数据",mappingUrl = "/role_info_list",type = PermissionType.FUNCTION)
    @GetMapping("/role_info_list")
    public ApiResponse<List<RoleInfoVo>> getRoleInfoList(){
        return roleService.getRoleInfoList();
    }



}
