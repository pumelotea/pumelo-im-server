package io.pumelo.im.controller;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/v1")
@Component
public class WsController {
    @OnOpen
    public void onOpen(Session session) throws IOException {

    }

    @OnClose
    public void onClose(Session session) {

    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {

    }
}
