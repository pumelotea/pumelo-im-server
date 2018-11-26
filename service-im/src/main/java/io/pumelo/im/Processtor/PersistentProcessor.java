package io.pumelo.im.Processtor;

import io.pumelo.im.model.Message;

public interface PersistentProcessor {

    void persistent(Message message,boolean isSent);
}
