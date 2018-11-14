package io.pumleo.im.service;

import im.model.APIRespones;
import io.pumelo.common.web.ApiResponse;
import io.pumelo.data.im.repo.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserEntityRepo userEntityRepo;


    /**
     * 登录
     *
     * @return
     */
    public ApiResponse login(String username, String password) {
        return null;
    }

    /**
     * 注册
     *
     * @return
     */
    public APIRespones register(String name, String password) {
        return null;
    }

    /**
     * 退出
     */
    public APIRespones logout() {
        return null;
    }


    public APIRespones search() {
        return null;
    }
}
