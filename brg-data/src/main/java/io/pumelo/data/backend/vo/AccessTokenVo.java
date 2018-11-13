package io.pumelo.data.backend.vo;


public class AccessTokenVo {
    private String access_token;
    private String token_type;
    private long expires_at;

    public AccessTokenVo() {
    }

    public AccessTokenVo(String access_token, String token_type, long expires_at) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_at = expires_at;
    }

    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public String getToken_type() {
        return token_type;
    }
    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
    public long getExpires_at() {
        return expires_at;
    }
    public void setExpires_at(long expires_at) {
        this.expires_at = expires_at;
    }
}
