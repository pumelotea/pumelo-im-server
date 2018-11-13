package io.pumelo.data.im.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '用户id 数字编号'")
    private Integer uid;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '姓名'")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '性别'")
    private String sex;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '手机号'")
    private String phone;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '生日'")
    private String birth;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '密码加盐'")
    private String salt;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '密码'")
    private String password;
}
