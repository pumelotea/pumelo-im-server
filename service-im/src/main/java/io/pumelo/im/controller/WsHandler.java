package io.pumelo.im.controller;

import com.alibaba.fastjson.JSON;
import io.pumelo.data.im.vo.AccessTokenVo;
import io.pumelo.im.IMContext;
import io.pumelo.im.model.Message;
import io.pumelo.im.model.SessionUser;
import io.pumelo.im.service.AuthService;
import io.pumelo.redis.ObjectRedis;
import io.pumelo.utils.IdealTokenUtils;
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

    @Autowired
    private AuthService authService;


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //清除在线状态 2条
        SessionUser sessionUser = IMContext.sessionUsers.remove(session.getId());
        if (sessionUser != null) {
            IMContext.sessionUsers.remove(sessionUser.getUid());
            LOG.info(this,"OFF LINE User:"+sessionUser.getUid()+" session:"+session.getId());
        }


    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token ="bearer " + session.getUri().getRawQuery();
        if (StringUtils.isBlank(token)) {
            //返回token无效
            IMContext.send(session,Message.makeSysMsg("", "身份认证失败", "TEXT", "60006"));
            return;
        }
        String uid = IdealTokenUtils.getSubject(authService.getJwtSecret(), token);
        if (StringUtils.isBlank(uid)) {
            //返回token无效
            IMContext.send(session,Message.makeSysMsg(uid, "身份认证失败", "TEXT", "60006"));
            return;
        }
        AccessTokenVo accessTokenVo = redis.get("user/" + uid, AccessTokenVo.class);
        if (accessTokenVo == null){
            //返回token无效
            IMContext.send(session,Message.makeSysMsg(uid, "身份认证失败", "TEXT", "60006"));
            return;
        }

        //创建会话用户
        SessionUser sessionUser = new SessionUser(uid, session);

        //插入到在线用户表 2条 ，session-id 对应 用户 || uid 对应 用户
        IMContext.sessionUsers.put(session.getId(), sessionUser);
        IMContext.sessionUsers.put(uid, sessionUser);
        LOG.info(this,"ON LINE User:"+sessionUser.getUid()+" session:"+session.getId());
        //返回连接成功
        IMContext.send(session,Message.makeSysMsg(uid, "连接成功", "TEXT", "0"));
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
            IMContext.sendToUser(msg);
            IMContext.sendAck(session,msg);
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
