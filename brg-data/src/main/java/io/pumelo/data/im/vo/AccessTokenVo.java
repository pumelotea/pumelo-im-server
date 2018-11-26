package io.pumelo.data.im.vo;


import io.pumelo.data.im.vo.user.UserVo;
import lombok.Data;

@Data
public class AccessTokenVo {
    private String uid;
    private UserVo userVo;
    private String accessToken;
    private String tokenType;
    private long expiresAt;

    public AccessTokenVo() {
    }

    public AccessTokenVo(String accessToken, String tokenType, long expiresAt) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresAt = expiresAt;
    }
}
