package io.pumelo.im.controller;

import io.pumelo.authorizion.AuthFilter;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.vo.message.MessageListVo;
import io.pumelo.data.im.vo.message.MessagePreviewVo;
import io.pumelo.im.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @AuthFilter
    @GetMapping("/message_preview")
    public ApiResponse<List<MessagePreviewVo>> getOfflineMessagePreview(){
        return messageService.getOfflineMessagePreview();
    }

    @AuthFilter
    @GetMapping("/offline_message/{uid}")
    public ApiResponse<MessageListVo> getOffineMessage(@PathVariable String uid){
        return messageService.getOffineMessage(uid);
    }

}
