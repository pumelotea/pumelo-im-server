package io.pumelo.data.im.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "group")
public class GroupEntity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '主键'")
    private String groupId;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '群组名称'")
    private String groupName;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '所属用户'")
    private String uid;
}
