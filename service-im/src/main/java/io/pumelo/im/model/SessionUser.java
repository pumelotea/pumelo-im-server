package io.pumelo.im.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.socket.WebSocketSession;

/**
 * 基于websocket实现的长连接会话
 */
public class SessionUser implements Session<WebSocketSession> {
    private String uid;
    private long loginAt;

    @JsonIgnore
    private transient WebSocketSession session;

    @Override
    public void init(String uid, WebSocketSession session) {
        this.loginAt = System.currentTimeMillis();
        this.uid = uid;
        this.session = session;
    }

    @Override
    public WebSocketSession getConnection() {
        return this.session;
    }

    @Override
    public String getUid() {
        return uid;
    }

    @Override
    public long getLoginAt() {
        return loginAt;
    }
}
