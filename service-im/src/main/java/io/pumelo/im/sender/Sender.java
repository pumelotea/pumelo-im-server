package io.pumelo.im.sender;

import io.pumelo.im.model.Message;

import java.io.IOException;

/**
 * 消息发送接口
 * @param <T> 长连接对象
 */
public interface Sender<T> {
    void sendToUser(String uid, Message message) throws Exception;
    void send(T session, Message message) throws IOException;
}
