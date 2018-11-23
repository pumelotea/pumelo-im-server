package io.pumelo.im.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;


@Data
public class SessionUser {
    private String uid;
    private long loginAt;

    @JsonIgnore
    private transient WebSocketSession session;

    public SessionUser(String uid, WebSocketSession session) {
        this.uid = uid;
        this.loginAt = System.currentTimeMillis();
        this.session = session;
    }
}
