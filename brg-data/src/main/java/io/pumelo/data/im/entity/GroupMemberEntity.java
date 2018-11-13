package io.pumelo.data.im.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "group_member")
public class GroupMemberEntity {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '主键'")
    private String memberId;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '群组id'")
    private String groupId;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '成员用户id'")
    private String uid;
}
