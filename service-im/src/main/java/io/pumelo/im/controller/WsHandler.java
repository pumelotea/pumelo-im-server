package io.pumelo.im.controller;

import com.alibaba.fastjson.JSON;
import io.pumelo.data.im.vo.AccessTokenVo;
import io.pumelo.idgens.worker.IdWorker;
import io.pumelo.im.model.Message;
import io.pumelo.im.model.Session;
import io.pumelo.im.model.SessionUser;
import io.pumelo.im.processtor.PersistentProcessor;
import io.pumelo.im.router.Router;
import io.pumelo.im.sender.Sender;
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


@Component
public class WsHandler extends TextWebSocketHandler {

    @Autowired
    private ObjectRedis redis;
    @Autowired
    private AuthService authService;
    @Autowired
    private Router router;
    @Autowired
    private Sender sender;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private PersistentProcessor persistentProcessor;


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        //清除在线状态
        Session user = router.removeUserBySessionId(session.getId());
        if (user != null) {
            LOG.info(this,"OFF LINE User:"+user.getUid()+" session:"+session.getId());
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token ="bearer " + session.getUri().getRawQuery();
        if (StringUtils.isBlank(token)) {
            //返回token无效
            sender.send(session,Message.makeSysMsg("", "身份认证失败", "TEXT", "60006"));
            return;
        }
        String uid = IdealTokenUtils.getSubject(authService.getJwtSecret(), token);
        if (StringUtils.isBlank(uid)) {
            //返回token无效
            sender.send(session,Message.makeSysMsg(uid, "身份认证失败", "TEXT", "60006"));
            return;
        }
        AccessTokenVo accessTokenVo = redis.get("user/" + uid, AccessTokenVo.class);
        if (accessTokenVo == null){
            //返回token无效
            sender.send(session,Message.makeSysMsg(uid, "身份认证失败", "TEXT", "60006"));
            return;
        }

        //创建会话用户
        SessionUser sessionUser = new SessionUser();
        sessionUser.init(uid,session);

        //建立路由
        router.setUser(sessionUser);
        LOG.info(this,"ON LINE User:"+sessionUser.getUid()+" session:"+session.getId());
        //返回连接成功
        sender.send(session,Message.makeSysMsg(uid, "连接成功", "TEXT", "0"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        LOG.info(this,message.getPayload());
        //消息上行处理
        Message msg = msgDecoder(message.getPayload());
        //保障消息一定是本人发出
        msg.makeComplete(router.getUserBySessionId(session.getId()).getUid(),idWorker.nextId());
        //保障消息体的合法性
        msg.check();

        if (msg.getMsgType().equals("HEART")){
            //心跳处理
//            IMContext.sendHeart(session);
        } else {
            try{
                sender.sendToUser(msg.getFrom(),msg);
                if (router.isOnline(msg.getTo())){
                    sender.sendToUser(msg.getTo(),msg);
                    persistentProcessor.persistent(msg,true);
                }else {
                    //持久化
                    persistentProcessor.persistent(msg,false);
                }
            }catch (Exception e){
                //持久化
                persistentProcessor.persistent(msg,false);
            }
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
