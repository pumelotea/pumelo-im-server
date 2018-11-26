package io.pumelo.im.Processtor;

import io.pumelo.data.im.entity.MessageEntity;
import io.pumelo.data.im.repo.MessageEntityRepo;
import io.pumelo.im.model.Message;
import io.pumelo.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MySQLPersistentProcessor implements PersistentProcessor {
    @Autowired
    private MessageEntityRepo messageEntityRepo;

    @Override
    public void persistent(Message message,boolean isSent) {
        if (message == null) return;
        MessageEntity messageEntity = new MessageEntity();
        BeanUtils.copyAttrs(messageEntity,message);
        messageEntity.setIsSent(isSent);
        messageEntityRepo.save(messageEntity);
    }
}
