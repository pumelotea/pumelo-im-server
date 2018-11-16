package io.pumelo.data.im.vo;

import io.pumelo.data.backend.vo.AccessTokenVo;
import io.pumelo.data.im.vo.user.UserVo;
import lombok.Data;

@Data
public class LoginVo {
    private UserVo userVo;
    private AccessTokenVo accessTokenVo;

    public LoginVo() {
    }

    public LoginVo(UserVo userVo, AccessTokenVo accessTokenVo) {
        this.userVo = userVo;
        this.accessTokenVo = accessTokenVo;
    }
}
