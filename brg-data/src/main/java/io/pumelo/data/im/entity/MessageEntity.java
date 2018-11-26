package io.pumelo.data.im.entity;

import io.pumelo.db.entity.AbstractBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "im_message")
@Data
@EqualsAndHashCode(callSuper = false)
public class MessageEntity extends AbstractBaseEntity {
    @Id
    @Column
    private Long messageId;
    @Column
    private String code;
    @Column
    private String msgType;//SYS ,USER ,GROUP,HEART
    @Column
    private String contentType;//内容类型, //PICTURE,TEXT,FILE,ASKUSER,ASKGROUP
    @Column
    private String content;
    @Column(name = "send_from")
    private String from;
    @Column(name = "send_to")
    private String to;//由类型决定是好友消息还是群组消息
    @Column
    private Long sentAt;//发送时间
    @Column
    private Boolean isSent;


}
