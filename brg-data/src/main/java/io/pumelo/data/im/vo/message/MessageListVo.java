package io.pumelo.data.im.vo.message;


import io.pumelo.data.im.entity.MessageEntity;
import lombok.Data;

import java.util.List;

@Data
public class MessageListVo {
    private boolean hasNext;
    private List<MessageEntity> messageList;
}
