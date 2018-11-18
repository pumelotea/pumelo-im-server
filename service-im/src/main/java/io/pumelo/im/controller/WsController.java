package io.pumelo.im.controller;

import com.alibaba.fastjson.JSON;
import io.pumelo.im.IMContext;
import io.pumelo.im.model.Message;
import io.pumelo.im.model.SessionUser;
import io.pumelo.redis.ObjectRedis;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * 这里的token 不是jwt
 */
@ServerEndpoint(value = "/v1/{token}")
@Component
public class WsController {
    @Autowired
    private ObjectRedis redis;


    @OnOpen
    public void onOpen(Session session) throws IOException {
        String token = session.getPathParameters().get("token");
        //鉴权
        String uid = redis.get(token,String.class);
        if (StringUtils.isBlank(uid)){
            //返回token无效
            IMContext.send(Message.makeSysMsg(uid,"auth fail","TEXT","1001"));
            return;
        }

        //创建会话用户
        SessionUser sessionUser = new SessionUser(uid,session);

        //插入到在线用户表 2条 ，session-id 对应 用户 || uid 对应 用户
        IMContext.sessionUsers.put(session.getId(),sessionUser);
        IMContext.sessionUsers.put(uid,sessionUser);

        //返回连接成功
        IMContext.send(Message.makeSysMsg(uid,"connected","TEXT","0"));

    }

    @OnClose
    public void onClose(Session session) {
        //清除在线状态 2条
        SessionUser sessionUser = IMContext.sessionUsers.remove(session.getId());
        if (sessionUser != null){
            IMContext.sessionUsers.remove(sessionUser.getUid());
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        //消息上行处理
        Message msg = msgDecoder(message);
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

    private Message msgDecoder(String message){
        return JSON.parseObject(message,Message.class);
    }
}
