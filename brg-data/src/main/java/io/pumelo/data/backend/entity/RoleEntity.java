package io.pumelo.data.backend.entity;

import io.pumelo.common.entity.RoleTag;
import io.pumelo.db.entity.AbstractBaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class RoleEntity extends AbstractBaseEntity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(length = 36)
    private String roleId;
    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '角色名称'")
    private String roleName;
    @Column(columnDefinition = "varchar(255) COMMENT '角色描述'")
    private String roleDescription;
    private RoleTag roleTag;

    public RoleEntity() {
        //该信息目前不开放
        this.roleTag = RoleTag.COMMON_ROLE;
    }

    public RoleEntity(String roleName, String roleDescription, RoleTag roleTag) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
        this.roleTag = roleTag;
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

    public RoleTag getRoleTag() {
        return roleTag;
    }

    public void setRoleTag(RoleTag roleTag) {
        this.roleTag = roleTag;
    }

    public void update(String roleName,String roleDescription) {
        this.roleName = roleName;
        this.roleDescription = this.roleDescription;
    }

    public void delete(){
        this.isTrash = true;
    }

    public boolean isSystemRole(){
        return "system".equals(this.createdBy);
    }
}
