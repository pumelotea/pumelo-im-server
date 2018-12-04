package io.pumelo.im.sender;

import io.pumelo.im.model.Message;
import io.pumelo.im.model.Session;
import io.pumelo.im.router.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * 基于本地websocket实现的发送组件
 */
@Component
public class WsSender implements Sender<WebSocketSession> {
    @Autowired
    private Router router;

    @Override
    public void sendToUser(String uid, Message message) throws Exception {
        Session session = router.getUser(uid);
        WebSocketSession webSocketSession = (WebSocketSession) session.getConnection();
        synchronized (webSocketSession){
            if (webSocketSession.isOpen()){
                webSocketSession.sendMessage(message.toTextMessage());
            }
        }
    }

    @Override
    public void send(WebSocketSession webSocketSession, Message message) throws IOException {
        synchronized (webSocketSession){
            if (webSocketSession.isOpen()){
                webSocketSession.sendMessage(message.toTextMessage());
            }
        }
    }
}
