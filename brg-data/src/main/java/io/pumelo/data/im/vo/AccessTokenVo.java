package io.pumelo.data.im.vo;


import lombok.Data;

@Data
public class AccessTokenVo {
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
