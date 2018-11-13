package im.controller;

import com.alibaba.fastjson.JSON;
import io.pumelo.im.model.Message;
import io.pumelo.im.model.User;
import io.pumelo.im.utils.ChineseName;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Hashtable;
import java.util.UUID;

import static io.pumelo.im.IMContext.onlineUsers;

@ServerEndpoint(value = "/chat")
@Component
public class IMController {
    //session id - user

    @OnOpen
    public void onOpen(Session session) throws IOException {
        User user = new User(session.getId(), ChineseName.getRandomName(),System.currentTimeMillis(),session);
        onlineUsers.put(session.getId(),user);
        session.getBasicRemote().sendText(JSON.toJSONString(user));
    }

    @OnClose
    public void onClose(Session session) {
        onlineUsers.remove(session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println(message);
        User user = onlineUsers.get(session.getId());
        String name = "OFFLINE USER";
        if (user != null){
            name = user.getName();
        }

        if (message.length()>512){
            Message msg = new Message(session.getId(),name,UUID.randomUUID().toString(),System.currentTimeMillis(),"发送长度不允许超过512");
            session.getBasicRemote().sendText(JSON.toJSONString(msg));
            return;
        }


        Message msg = new Message(session.getId(),name,UUID.randomUUID().toString(),System.currentTimeMillis(),message);
        sendAll(msg);
    }


    public void sendAll(Message message){
        onlineUsers.forEach((uid,user)->{
            try {
                user.getSession().getBasicRemote().sendText(JSON.toJSONString(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
