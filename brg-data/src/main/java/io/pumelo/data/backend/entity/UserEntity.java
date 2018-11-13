package io.pumelo.data.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.pumelo.db.entity.AbstractBaseEntity;
import io.pumelo.utils.EncryptionUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * 平台用户鉴权
 * 用户其他属性应当单独一张分表
 */
@Entity
@Table(name = "user")
public class UserEntity extends AbstractBaseEntity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(length = 36)
    private String userId;
    @Column(nullable = false, unique = true, columnDefinition = "varchar(50) COMMENT '用户名'")
    private String username;
    @JsonIgnore
    @Column(nullable = false, columnDefinition = "varchar(255) COMMENT '密码'")
    private String password;
    @Column(columnDefinition = "varchar(255) COMMENT '邮箱'")
    private String email;
    @Column(columnDefinition = "varchar(11) COMMENT '手机号码'")
    private String phone;
    @Column(nullable = false, columnDefinition = "bit(1) COMMENT '是否启用'")
    private Boolean isEnable;
    @Column(nullable = false)
    private String salt;



    public UserEntity() {

    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.isEnable = true;
        this.salt = UUID.randomUUID().toString().replaceAll("-","");
        this.password = EncryptionUtils.sha1(password+salt);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void resetPassword(String password) {
        this.password = EncryptionUtils.sha1(password+salt);
    }

    public void updateEnable() {
        this.isEnable = !this.isEnable;
    }

    public boolean isAuthentication(String password){
        return Objects.equals(this.password, EncryptionUtils.sha1(password+salt));
    }

    public void createRootUser(String username, String password, String phone, String email) {
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.isEnable = true;
        this.salt = UUID.randomUUID().toString().replaceAll("-","");
        this.password = EncryptionUtils.sha1(password+salt);
    }
}
