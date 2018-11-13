package io.pumelo.data.backend.vo;

public class LoginVo {
    private String userId;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private String headImg;
    private String level;
    private AccessTokenVo accessTokenVo;

    public LoginVo(AccessTokenVo accessTokenVo) {
        this.accessTokenVo = accessTokenVo;
    }

    public LoginVo() {
    }

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public AccessTokenVo getAccessTokenVo() {
        return accessTokenVo;
    }

    public void setAccessTokenVo(AccessTokenVo accessTokenVo) {
        this.accessTokenVo = accessTokenVo;
    }
}
