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
@Table(name = "friend_ask")
@Data
@EqualsAndHashCode(callSuper = false)
public class FriendAskEntity extends AbstractBaseEntity {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '主键'")
    private String friendAskId;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '我的id'")
    private String uid;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '目标用户id'")
    private String targetUid;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '申请原因'")
    private String reason;

    @Column(nullable = false, columnDefinition = "bit(1) COMMENT '是否同意'")
    private Boolean isAgree;

    @Column(nullable = false, columnDefinition = "bit(1) COMMENT '是否处理'")
    private Boolean isProcess;

    public static FriendAskEntity ask(String uid,String targetUid,String reason){
        FriendAskEntity friendAskEntity = new FriendAskEntity();
        friendAskEntity.uid  = uid;
        friendAskEntity.targetUid = targetUid;
        friendAskEntity.reason = reason;
        friendAskEntity.isAgree = false;
        friendAskEntity.isProcess = false;
        return friendAskEntity;
    }
}
