package io.pumelo.data.backend.dto.role;

import io.pumelo.data.backend.struct.PermissionUnit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;


/**
 * 用于角色权限的绑定关系
 */
@ApiModel(description = "角色数据对象")
public class RolePermissionDto {

    @ApiModelProperty(value = "roleId",required = true)
    @Size(min = 36,max = 36,message = "角色Id")
    @NotEmpty(message = "角色Id不可为空")
    private String roleId;

    @ApiModelProperty(value = "permission")
    private List<PermissionUnit> permission;

    public RolePermissionDto() {
    }

    public RolePermissionDto(String roleId, List<PermissionUnit> permission) {
        this.roleId = roleId;
        this.permission = permission;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<PermissionUnit> getPermission() {
        return permission;
    }

    public void setPermission(List<PermissionUnit> permission) {
        this.permission = permission;
    }

}
