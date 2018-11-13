package io.pumelo.data.backend.entity;

import io.pumelo.db.entity.AbstractBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "role_permission")
public class RolePermissionEntity extends AbstractBaseEntity {

    public RolePermissionEntity() {
    }

    public RolePermissionEntity(String roleId, String permissionKey) {
        this.roleId = roleId;
        this.permissionKey = permissionKey;
    }

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(length = 36)
    private String rolePermissionId;
    @Column(nullable = false, columnDefinition = "varchar(36) COMMENT '角色ID'")
    private String roleId;
    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '权限ID'")
    private String permissionKey;

    public String getRolePermissionId() {
        return rolePermissionId;
    }

    public void setRolePermissionId(String rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }
}
