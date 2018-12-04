package io.pumelo.im.sender;

import io.pumelo.im.model.Message;

import java.io.IOException;

public interface Sender<T> {
    void sendToUser(String uid, Message message) throws Exception;
    void send(T session, Message message) throws IOException;
}
