package io.pumelo.im.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.websocket.Session;

@Data
public class SessionUser {
    private String uid;
    private long loginAt;

    @JsonIgnore
    private transient Session session;

    public SessionUser(String uid, Session session) {
        this.uid = uid;
        this.loginAt = System.currentTimeMillis();
        this.session = session;
    }
}
