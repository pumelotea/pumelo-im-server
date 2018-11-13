package io.pumelo.data.backend.struct;

import io.pumelo.common.basebean.PermissionType;

import java.util.List;

/**
 * 权限非叶子节点数据结构
 * author: pumelo
 * 2018/3/21
 */
public class PermissionUnit {
    private String permissionKey;
    private String permissionName;
    private String parentKey;
    private PermissionType permissionType;
    private Integer sortNum;
    private String icon;
    private String url;
    private String method;

    //在Leaf节点中一定为空,上游节点可以为空,但是不一定为空
    private List<PermissionUnit> permissionBranch;

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public PermissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public List<PermissionUnit> getPermissionBranch() {
        return permissionBranch;
    }

    public void setPermissionBranch(List<PermissionUnit> permissionBranch) {
        this.permissionBranch = permissionBranch;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
