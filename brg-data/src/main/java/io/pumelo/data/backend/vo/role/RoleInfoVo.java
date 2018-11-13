package io.pumelo.data.backend.vo.role;


/**
 * 角色信息
 * 不包含权限信息
 */
public class RoleInfoVo {
    private String roleId;
    private String roleName;
    private String roleDescription;

    public RoleInfoVo() {
    }

    public RoleInfoVo(String roleId, String roleName, String roleDescription) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
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
}
