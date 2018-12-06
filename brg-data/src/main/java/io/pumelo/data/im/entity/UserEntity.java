package io.pumelo.data.im.entity;

import io.pumelo.db.entity.AbstractBaseEntity;
import io.pumelo.utils.EncryptionUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "im_user")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends AbstractBaseEntity {

    @Id
    @Column(nullable = false)
    private String uid;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '姓名'")
    private String name;

    @Column(columnDefinition = "varchar(100) COMMENT '性别'")
    private String sex;

    @Column(columnDefinition = "varchar(100) COMMENT '手机号'")
    private String phone;

    @Column(columnDefinition = "varchar(100) COMMENT '生日'")
    private String birth;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '密码加盐'")
    private String salt;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '密码'")
    private String password;

    @Column(columnDefinition = "text")
    private String headImg;


    public boolean isAuthentication(String password) {
        return Objects.equals(this.password, EncryptionUtils.sha1(password + salt));
    }
}
