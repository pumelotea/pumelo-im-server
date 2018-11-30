package io.pumelo.im.controller;

import io.pumelo.common.web.ApiResponse;
import io.pumelo.im.service.VideoChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.io.IOException;

@RestController
public class VideoChatController {
    @Autowired
    private VideoChatService videoChatService;

    @PostMapping("/signal/{uid}")
    public ApiResponse transferToFriend(@PathVariable @NotBlank String uid, @RequestParam @NotBlank  String json) throws IOException {
        return videoChatService.transferToFriend(uid,json);
    }

}
