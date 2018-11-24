package io.pumelo.im;


import io.pumelo.im.model.Message;
import io.pumelo.im.model.SessionGroup;
import io.pumelo.im.model.SessionUser;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Hashtable;

/**
 * 全局上下文对象
 * 全局不使用数据库中的memberId
 */
public class IMContext {
    //在线用户表
    public static Hashtable<String, SessionUser> sessionUsers = new Hashtable<>();
    //全局群组缓存
    public static Hashtable<String, SessionGroup> sessionGroups = new Hashtable<>();


    public static void send(WebSocketSession session,Message message) throws IOException {
        if (session.isOpen()){
            session.sendMessage(message.toTextMessage());
        }else {
            //离线处理
        }
    }


    public static void sendToUser(Message message) throws IOException {
        SessionUser sessionUser = sessionUsers.get(message.getTo());
        if (sessionUser != null){
            send(sessionUser.getSession(),message);


        }else {
           //离线处理
        }
    }

    public static void sendToGroup(Message message){

    }

    public static void sendHeart(WebSocketSession session) throws IOException {
        session.sendMessage(Message.makeHeartMsg().toTextMessage());
    }



    public static boolean userIsOnline(String uid) {
        return sessionUsers.contains(uid);
    }

    public static boolean groupIsInSession(String groupId) {
        return sessionGroups.contains(groupId);
    }

    public static SessionUser getUser(WebSocketSession session){
        return sessionUsers.get(session.getId());
    }
}
