package io.pumelo.im.router;

import io.pumelo.im.model.Session;

public interface Router {
    Session getUser(String uid);
    void setUser(Session user);
    Session removeUser(String uid);
    int onlineTotalUsers();
    boolean isOnline(String uid);
    Session getUserBySessionId(String sessionId);
    Session removeUserBySessionId(String sessionId);
}
