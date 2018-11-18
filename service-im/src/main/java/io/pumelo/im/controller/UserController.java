package io.pumelo.im.controller;

import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.vo.AccessTokenVo;
import io.pumelo.data.im.vo.user.UserVo;
import io.pumelo.im.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ApiResponse<AccessTokenVo> login(@RequestParam String uid, @RequestParam String password) {
        return  userService.login(uid, password);
    }

    @PostMapping("/register")
    public ApiResponse<UserVo> register(@RequestParam String name,@RequestParam  String password) {
        return userService.register(name, password);
    }

    @PostMapping("/logout")
    public ApiResponse logout() {
        return userService.logout();
    }
}
