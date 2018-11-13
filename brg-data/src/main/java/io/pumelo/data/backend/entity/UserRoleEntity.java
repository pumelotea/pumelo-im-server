package io.pumelo.data.backend.entity;

import io.pumelo.db.entity.AbstractBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class UserRoleEntity extends AbstractBaseEntity {
    public UserRoleEntity() {
    }

    public UserRoleEntity(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(length = 36)
    private String userRoleId;
    @Column(nullable = false, columnDefinition = "varchar(36) COMMENT '用户ID'")
    private String userId;
    @Column(nullable = false, columnDefinition = "varchar(36) COMMENT '角色ID'")
    private String roleId;

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
