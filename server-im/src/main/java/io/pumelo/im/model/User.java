package io.pumelo.im.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.websocket.Session;

@Data
public class User {
    private String uid;
    private String name;
    private long loginAt;

    @JsonIgnore
    private transient Session session;

    public User(String uid, String name, long loginAt,Session session) {
        this.uid = uid;
        this.name = name;
        this.loginAt = loginAt;
        this.session = session;
    }
}
