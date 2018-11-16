package io.pumelo.data.im.entity;

import io.pumelo.db.entity.AbstractBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "group")
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupEntity  extends AbstractBaseEntity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '主键'")
    private String groupId;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '群组名称'")
    private String groupName;

    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '所属用户'")
    private String uid;

    public static GroupEntity create(String groupName,String uid){
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.groupName = groupName;
        groupEntity.uid = uid;
        return groupEntity;
    }
}
