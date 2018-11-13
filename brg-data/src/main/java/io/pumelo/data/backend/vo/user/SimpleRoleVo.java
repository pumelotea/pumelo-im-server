package io.pumelo.data.backend.vo.user;

/**
 * 用于获取角色列表
 * Created by Fyb
 * on 2017/10/31.
 */
public class SimpleRoleVo {
    private String roleId;
    private String roleName;
    private String branchId;
    private String branchName;

    public SimpleRoleVo() {
    }

    public SimpleRoleVo(String roleId, String roleName, String branchId) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.branchId = branchId;
    }

    public SimpleRoleVo(String roleId, String roleName, String branchId, String branchName) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.branchId = branchId;
        this.branchName = branchName;
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

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
