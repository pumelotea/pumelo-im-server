package io.pumelo.data.backend.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


/**
 * 用于角色创建
 */
@ApiModel(description = "角色")
public class RoleDto {

    @ApiModelProperty(value = "roleName",required = true)
    @Size(min = 1,max = 100,message = "角色名称1-100字符")
    @NotEmpty(message = "角色名称不可为空")
    private String roleName;

    @ApiModelProperty(value = "roleDescription")
    @Size(max = 100,message = "角色描述1-100字符" )
    private String roleDescription;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
