package io.pumelo.data.backend.entity;

import io.pumelo.common.basebean.PermissionType;
import io.pumelo.db.entity.AbstractBaseEntity;

import javax.persistence.*;


@Entity
@Table(name = "permission")
public class PermissionEntity extends AbstractBaseEntity {

    @Id
    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '主键'")
    private String permissionKey;
    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '菜单名'")
    private String permissionName;
    @Column(columnDefinition = "varchar(100) COMMENT '上一级菜单'")
    private String parentKey;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PermissionType permissionType;

    @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '映射地址'")
    private String url;

    @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '请求方法，服务器校验用'")
    private String method;

    @Column(nullable = false, columnDefinition = "int(11) COMMENT '排序号'")
    private Integer sortNum;
    @Column(columnDefinition = "varchar(255) COMMENT 'icon图标'")
    private String icon;

    public PermissionEntity() {
    }

    public PermissionEntity(String permissionKey, String permissionName, String parentKey, PermissionType permissionType, String url, Integer sortNum,String method) {
        this.permissionKey = permissionKey;
        this.permissionName = permissionName;
        this.parentKey = parentKey;
        this.permissionType = permissionType;
        this.url = url;
        this.sortNum = sortNum;
        this.method = method;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
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
