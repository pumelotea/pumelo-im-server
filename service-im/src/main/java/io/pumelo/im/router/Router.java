package io.pumelo.im.router;

import io.pumelo.im.model.Session;

/**
 * 路由接口
 */
public interface Router {
    Session getUser(String uid);
    void setUser(Session user);
    Session removeUser(String uid);
    int onlineTotalUsers();
    boolean isOnline(String uid);
    Session getUserBySessionId(String sessionId);
    Session removeUserBySessionId(String sessionId);
}
