package io.pumelo.im.controller;

import io.pumelo.im.IMContext;
import io.pumelo.im.model.APIRespones;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/online_users")
    public APIRespones getOnlineUsers(){
        return APIRespones.success(IMContext.getUserList());
    }

}
