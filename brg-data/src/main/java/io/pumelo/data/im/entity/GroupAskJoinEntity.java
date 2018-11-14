package io.pumelo.data.im.entity;

import io.pumelo.db.entity.AbstractBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 加群申请记录表
 */
@Entity
@Table(name = "group_ask_join")
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupAskJoinEntity extends AbstractBaseEntity {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '主键'")
    private String groupAskId;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '群组id'")
    private String groupId;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '用户id'")
    private String uid;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '申请原因'")
    private String reason;

    @Column(nullable = false, columnDefinition = "bit(1) COMMENT '是否同意'")
    private Boolean isAgree;

    @Column(nullable = false, columnDefinition = "bit(1) COMMENT '是否处理'")
    private Boolean isProcess;
}
