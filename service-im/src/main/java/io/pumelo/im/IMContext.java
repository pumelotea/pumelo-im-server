package io.pumelo.im;


import io.pumelo.idgens.worker.IdWorker;
import io.pumelo.im.Processtor.PersistentProcessor;
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

    public static IdWorker idWorker;
    //在线用户表
    public static Hashtable<String, SessionUser> sessionUsers = new Hashtable<>();
    //全局群组缓存
    public static Hashtable<String, SessionGroup> sessionGroups = new Hashtable<>();

    private static PersistentProcessor persistentProcessor;

    /**
     * 设置持久化处理器
     * @param persistentProcessor
     */
    public static void setPersistentProcessor(PersistentProcessor persistentProcessor){
        IMContext.persistentProcessor = persistentProcessor;
    }

    public static void setIdWorker(IdWorker idWorker){
        IMContext.idWorker = idWorker;
    }

    /**
     * 返回确认 不支持持久化
     * @param session
     * @param message
     * @throws IOException
     */
    public static void sendAck(WebSocketSession session,Message message) throws IOException {
        if (session.isOpen()){
            session.sendMessage(message.toTextMessage());
        }
    }

    public static void send(WebSocketSession session,Message message) throws IOException {
        if (session.isOpen()){
            synchronized (session) {
                if (session.isOpen()){
                    session.sendMessage(message.toTextMessage());
                }
            }
            persistentProcessor.persistent(message,true);
        }else {
            //离线处理
            persistentProcessor.persistent(message,false);
        }
    }


    public static void sendToUser(Message message) throws IOException {
        SessionUser sessionUser = sessionUsers.get(message.getTo());
        if (sessionUser != null){
            send(sessionUser.getSession(),message);
        }else {
           //离线处理
            persistentProcessor.persistent(message,false);
        }
    }

    public static void sendToUserUnPersistent(Message message) throws IOException {
        SessionUser sessionUser = sessionUsers.get(message.getTo());
        if (sessionUser != null){
            WebSocketSession session = sessionUser.getSession();
            synchronized (session) {
                if (session.isOpen()){
                    session.sendMessage(message.toTextMessage());
                }
            }
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
