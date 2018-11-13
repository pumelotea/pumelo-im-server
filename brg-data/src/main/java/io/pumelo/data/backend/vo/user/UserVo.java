package io.pumelo.data.backend.vo.user;

/**
 * Created by F
 * on 2017/11/1.
 */
public class UserVo {
    private String userId;
    private String username;
    private String roleId;
    private String roleName;
    private Boolean isEnable;

    public UserVo() {
    }

    public UserVo(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }
}
