package io.pumelo.data.im.vo.askFriend;

import lombok.Data;

@Data
public class AskFriendVo {
    private String friendAskId;

    private String uid;

    private String name;

    private String sex;

    private String targetUid;

    private String reason;

    private Boolean isAgree;

    private Boolean isProcess;

    private long createdAt;
}
