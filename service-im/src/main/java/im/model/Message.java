package im.model;

import lombok.Data;

@Data
public class Message {
    private String uid;
    private String name;
    private String messageId;
    private long sentAt;
    private String content;

    public Message(String uid, String name,String messageId, long sentAt, String content) {

        this.uid = uid;
        this.name = name;
        this.messageId = messageId;
        this.sentAt = sentAt;
        this.content = content;
    }
}
