package io.pumelo.data.backend.vo.permission;

import io.pumelo.data.backend.struct.PermissionUnit;

import javax.persistence.Column;
import java.util.List;

/**
 * 权限数据结构
 * author: pumelo
 * 2018/3/22
 */
public class PermissionVo {
    @Column
    private List<PermissionUnit> permission;

    public PermissionVo(List<PermissionUnit> permission) {
        this.permission = permission;
    }

    public List<PermissionUnit> getPermission() {
        return permission;
    }

    public void setPermission(List<PermissionUnit> permission) {
        this.permission = permission;
    }
}
