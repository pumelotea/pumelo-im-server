package io.pumelo.data.im.entity;


import io.pumelo.db.entity.AbstractBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "friend")
@Data
@EqualsAndHashCode(callSuper = false)
public class FriendEntity extends AbstractBaseEntity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '主键'")
    private String friendId;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '我的id'")
    private String uid;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '好友id'")
    private String friendUid;

    public static FriendEntity makeFriend(String uid,String friendUid){
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.uid = uid;
        friendEntity.friendUid = friendUid;
        return friendEntity;
    }

}
