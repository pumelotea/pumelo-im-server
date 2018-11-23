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

    /**
     * 消息发送方法
     *
     * @param message
     */
    public static void send(Message message) throws IOException {
        if (message.getMsgType().equals("SYS")) {
            if (!userIsOnline(message.getTo())) {
                //离线消息存储
                return;
            }
            WebSocketSession session = sessionUsers.get(message.getTo()).getSession();
            if (!session.isOpen()){
                //离线消息存储
                return;
            }
            session.sendMessage(message.toTextMessage());

        } else if (message.getMsgType().equals("USER")) {
            if (!userIsOnline(message.getTo())) {
                //离线消息存储
                return;
            }
            WebSocketSession session = sessionUsers.get(message.getTo()).getSession();
            if (!session.isOpen()){
                //离线消息存储
                return;
            }
            session.sendMessage(message.toTextMessage());
        } else if (message.getMsgType().equals("GROUP")) {
            if (!groupIsInSession(message.getTo())) {
                return;
            }

            SessionGroup sessionGroup = sessionGroups.get(message.getTo());
            for (String uid : sessionGroup.getMemberSets()) {
                if (!userIsOnline(uid)) {
                    //离线消息存储
                    continue;
                }
                WebSocketSession session = sessionUsers.get(message.getTo()).getSession();
                if (!session.isOpen()){
                    //离线消息存储
                    continue;
                }
                session.sendMessage(message.toTextMessage());
            }
        }
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
