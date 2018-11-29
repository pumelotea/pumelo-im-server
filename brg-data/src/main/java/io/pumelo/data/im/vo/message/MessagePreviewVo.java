package io.pumelo.data.im.vo.message;


import io.pumelo.data.im.entity.MessageEntity;
import lombok.Data;


@Data
public class MessagePreviewVo {
    private int count;
    private MessageEntity message;
}
