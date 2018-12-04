package io.pumelo.im.router;

import io.pumelo.im.model.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Hashtable;

/**
 * 基于Hashtable和websocket实现的在线用户路由
 */
@Component
public class WsRouter implements Router {
    private static Hashtable<String, Session> sessionUsers = new Hashtable<>();
    private static Hashtable<String, String> sessionUids = new Hashtable<>();
    @Override
    public Session getUser(String uid) {
        return sessionUsers.get(uid);
    }

    @Override
    public void setUser(Session user) {
        WebSocketSession webSocketSession = (WebSocketSession) user.getConnection();
        sessionUids.put(webSocketSession.getId(),user.getUid());
        sessionUsers.put(user.getUid(),user);
    }

    @Override
    public Session removeUser(String uid) {
        sessionUids.remove(uid);
        return sessionUsers.remove(uid);
    }

    @Override
    public int onlineTotalUsers() {
        return sessionUsers.size();
    }

    @Override
    public boolean isOnline(String uid) {
        Session session = sessionUsers.get(uid);
        if (session == null){
            return false;
        }
        WebSocketSession socketSession = (WebSocketSession) session.getConnection();
        synchronized (socketSession){
           return socketSession.isOpen();
        }
    }

    @Override
    public Session getUserBySessionId(String sessionId){
       String uid = sessionUids.get(sessionId);
       if (uid == null){
           return null;
       }
       return getUser(uid);
    }

    @Override
    public Session removeUserBySessionId(String sessionId) {
        String uid = sessionUids.get(sessionId);
        if (uid == null){
            return null;
        }
        return sessionUsers.remove(uid);
    }
}
