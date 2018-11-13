package io.pumelo.controller;

import io.pumelo.authorizion.AuthFilter;
import io.pumelo.common.basebean.PermissionType;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.backend.dto.role.RoleDto;
import io.pumelo.data.backend.dto.role.RolePermissionDto;
import io.pumelo.data.backend.vo.role.RoleInfoVo;
import io.pumelo.data.backend.vo.role.RoleVo;
import io.pumelo.permission.annotation.PermissionFilter;
import io.pumelo.permission.annotation.PermissionRegister;
import io.pumelo.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * author: pumelo
 * 2018/3/22
 */
@RestController
@Validated
@Api(description = "角色相关")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "create_role",parentKey = "role_manage", name = "创建角色不包含权限",mappingUrl = "/role",type = PermissionType.FUNCTION)
    @PostMapping("/role")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName",value = "角色名称",paramType = "form", dataType = "string",required = true),
            @ApiImplicitParam(name = "roleDescription",value = "角色描述",paramType = "form", dataType = "string"),
    })
    public ApiResponse<RoleInfoVo> createRole(@Valid @ApiIgnore RoleDto roleDto){
        return roleService.createRole(roleDto);
    }

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "update_role",parentKey = "role_manage", name = "修改角色不包含权限",mappingUrl = "/role/{roleId}",type = PermissionType.FUNCTION)
    @PutMapping("/role/{roleId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId",value = "角色Id",paramType = "path", dataType = "string",required = true),
            @ApiImplicitParam(name = "roleName",value = "角色名称",paramType = "form", dataType = "string",required = true),
            @ApiImplicitParam(name = "roleDescription",value = "角色描述",paramType = "form", dataType = "string"),
    })
    public ApiResponse<RoleInfoVo> updateRole(@NotBlank(message = "角色Id不能为空") @Size(min = 36,max = 36,message = "角色Id长度36位(角色不能为空)") @PathVariable String roleId, @Valid @ApiIgnore RoleDto roleDto){
        return roleService.updateRole(roleId,roleDto);
    }

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "delete_role",parentKey = "role_manage", name = "删除角色",mappingUrl = "/role/{roleId}",type = PermissionType.FUNCTION)
    @DeleteMapping("/role/{roleId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId",value = "角色Id",paramType = "path", dataType = "string",required = true),
    })
    public ApiResponse deleteRole(@PathVariable("roleId") @NotBlank(message = "角色Id不能为空") @Size(min = 36,max = 36,message = "角色Id长度36位(角色不能为空)") String roleId){
        return roleService.deleteRole(roleId);
    }

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "assign_permission",parentKey = "role_manage", name = "赋予角色权限",mappingUrl = "/role/permission",type = PermissionType.FUNCTION)
    @PostMapping("/role/permission")
    public ApiResponse assignPermission(@Valid @RequestBody RolePermissionDto rolePermissionDto){
        return roleService.assignPermission(rolePermissionDto);
    }

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "get_role",parentKey = "role_manage", name = "获取单个角色不包含权限",mappingUrl = "/role/{roleId}",type = PermissionType.FUNCTION)
    @GetMapping("/role/{roleId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId",value = "角色Id",paramType = "path", dataType = "string",required = true),
    })
    public ApiResponse<RoleInfoVo> getRole(@NotBlank(message = "角色Id不能为空") @Size(min = 36,max = 36,message = "角色Id长度36位(角色不能为空)") @PathVariable String roleId){
        return roleService.getRole(roleId);
    }

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "get_role_permission",parentKey = "role_manage", name = "获取单个角色包含权限",mappingUrl = "/role_permission/{roleId}",type = PermissionType.FUNCTION)
    @GetMapping("/role_permission/{roleId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId",value = "角色Id",paramType = "path", dataType = "string",required = true),
    })
    public ApiResponse<RoleVo> getRolePermission(@NotBlank(message = "角色Id不能为空") @Size(min = 36,max = 36,message = "角色Id长度36位(角色不能为空)") @PathVariable String roleId){
        return roleService.getRolePermission(roleId);
    }

    @AuthFilter
    @PermissionFilter(filterType = PermissionType.FUNCTION)
    @PermissionRegister(key = "get_role_info_page",parentKey = "role_manage", name = "获取角色分页",mappingUrl = "/roles",type = PermissionType.FUNCTION)
    @GetMapping("/roles")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key",value = "搜索关键字",paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "page",value = "页码",paramType = "query", dataType = "int",required = true),
            @ApiImplicitParam(name = "size",value = "大小",paramType = "query", dataType = "int",required = true)
    })
    public ApiResponse<Page<RoleInfoVo>> getRoleInfoPage(String key, @Min(value = 0,message = "页码最小为0") @NotNull(message = "页码不能为空") Integer page,
                                                         @Min(value = 1,message = "数据长度最小为1") @NotNull(message = "数据长度不能为空")  Integer size){
        return roleService.getRoleInfoPage(key,page,size);
    }

}
