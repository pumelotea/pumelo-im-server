package io.pumelo.im.controller;

import com.alibaba.fastjson.JSON;
import io.pumelo.im.IMContext;
import io.pumelo.im.model.Message;
import io.pumelo.im.model.SessionUser;
import io.pumelo.redis.ObjectRedis;
import io.pumelo.utils.LOG;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Arrays;
import java.util.Map;

@Component
public class WsHandler extends TextWebSocketHandler {

    @Autowired
    private ObjectRedis redis;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        //清除在线状态 2条
        SessionUser sessionUser = IMContext.sessionUsers.remove(session.getId());
        if (sessionUser != null) {
            IMContext.sessionUsers.remove(sessionUser.getUid());
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String token = "";
        session.sendMessage(new TextMessage("!1111111"));
        //鉴权
        String uid = redis.get(token, String.class);
        if (StringUtils.isBlank(uid)) {
            //返回token无效
            IMContext.send(Message.makeSysMsg(uid, "auth fail", "TEXT", "1001"));
            return;
        }

        //创建会话用户
        SessionUser sessionUser = new SessionUser(uid, session);

        //插入到在线用户表 2条 ，session-id 对应 用户 || uid 对应 用户
        IMContext.sessionUsers.put(session.getId(), sessionUser);
        IMContext.sessionUsers.put(uid, sessionUser);

        //返回连接成功
        IMContext.send(Message.makeSysMsg(uid, "connected", "TEXT", "0"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        LOG.info(this,message.getPayload());
        //消息上行处理
        Message msg = msgDecoder(message.getPayload());
        //保障消息一定是本人发出
        msg.makeComplete(IMContext.getUser(session).getUid());
        //保障消息体的合法性
        msg.check();

        if (msg.getMsgType().equals("HEART")){
            //心跳处理
            IMContext.sendHeart(session);
        } else {
            IMContext.send(msg);
        }

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    private Message msgDecoder(String message){
        return JSON.parseObject(message,Message.class);
    }
}
