package io.pumelo.data.backend.vo.role;


import io.pumelo.data.backend.struct.PermissionUnit;

import java.util.List;

/**
 * 角色信息
 * 含权限信息
 */
public class RoleVo {
    private String roleId;
    private String roleName;
    private String roleDescription;
    private List<PermissionUnit> permission;

    public RoleVo(List<PermissionUnit> permission) {
        this.permission = permission;
    }

    public RoleVo(String roleId, String roleName, String roleDescription, List<PermissionUnit> permission) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
        this.permission = permission;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

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

    public List<PermissionUnit> getPermission() {
        return permission;
    }

    public void setPermission(List<PermissionUnit> permission) {
        this.permission = permission;
    }
}
