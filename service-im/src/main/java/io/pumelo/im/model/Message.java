package io.pumelo.im.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.UUID;

@Data
public class Message {
    private String messageId;
    private String msgType;//SYS ,USER ,GROUP
    private String contentType;//内容类型, //图片，文件，文本
    private String content;
    private String from;
    private String to;//由类型决定是好友消息还是群组消息
    private long sentAt;//发送时间


    public static Message makeSysMsg(String to,String content,String contentType){
        Message message = new Message();
        message.messageId = UUID.randomUUID().toString();
        message.msgType = "SYS";
        message.from = "SYS";
        message.sentAt = System.currentTimeMillis();
        message.to = to;
        message.content = content;
        message.contentType =contentType;
        return message;
    }

    public static Message makeUserMsg(String from, String to,String content,String contentType){
        Message message = new Message();
        message.messageId = UUID.randomUUID().toString();
        message.msgType = "USER";
        message.sentAt = System.currentTimeMillis();
        message.from =from;
        message.to = to;
        message.content = content;
        message.contentType =contentType;
        return message;
    }

    public static Message makeGroupMsg(String from, String to,String content,String contentType){
        Message message = new Message();
        message.messageId = UUID.randomUUID().toString();
        message.msgType = "GROUP";
        message.sentAt = System.currentTimeMillis();
        message.from =from;
        message.to = to;
        message.content = content;
        message.contentType =contentType;
        return message;
    }

    public String toJSON(){
        return JSON.toJSONString(this);
    }

}
