package io.pumelo.im.service;

import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.entity.MessageEntity;
import io.pumelo.data.im.repo.MessageEntityRepo;
import io.pumelo.data.im.vo.message.MessageListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageEntityRepo messageEntityRepo;
    @Autowired
    private AuthService authService;

    /**
     * 获取离线消息概览
     * 分组后每组取1条
     * @return
     */
    @Transactional
    public ApiResponse<List<MessageEntity>> getOfflineMessagePreview(){
        List<MessageEntity> listGroupByFrom = messageEntityRepo.findListGroupByFrom(authService.getId());
        listGroupByFrom.forEach(messageEntity -> {
            messageEntity.setIsSent(true);
            messageEntityRepo.save(messageEntity);
        });
        return ApiResponse.ok(listGroupByFrom);
    }


    /**
     * 获取单个用户的离线消息
     * 每次最多能获取20条
     * @param uid
     * @return
     */
    @Transactional
    public ApiResponse<MessageListVo> getOffineMessage(String uid){
        //取20条
        List<MessageEntity> listByfrom = messageEntityRepo.findListByfrom(uid, authService.getId(), PageRequest.of(0, 20));
        //修改状态
        listByfrom.forEach(messageEntity -> {
            messageEntity.setIsSent(true);
            messageEntityRepo.save(messageEntity);
        });
        //count
        int count = messageEntityRepo.countByFrom(uid,authService.getId());
        MessageListVo messageListVo = new MessageListVo();
        messageListVo.setMessageList(listByfrom);
        messageListVo.setHasNext(false);
        if (count>0){
            messageListVo.setHasNext(true);
        }
        return ApiResponse.ok(messageListVo);
    }

}
