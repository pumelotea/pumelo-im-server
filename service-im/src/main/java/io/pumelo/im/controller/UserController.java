package io.pumelo.im.controller;

import io.pumelo.authorizion.AuthFilter;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.vo.AccessTokenVo;
import io.pumelo.data.im.vo.user.UserSearchVo;
import io.pumelo.data.im.vo.user.UserVo;
import io.pumelo.im.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@Validated
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

    @AuthFilter
    @PostMapping("/logout")
    public ApiResponse logout() throws IOException {
        return userService.logout();
    }

    @AuthFilter
    @GetMapping("/search/users")
    public ApiResponse<Page<UserSearchVo>> search(@NotBlank String keyword, @NotNull int page,@NotNull int size) {
        return userService.search(keyword, page, size);
    }
}
