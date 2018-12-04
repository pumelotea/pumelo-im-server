package io.pumelo.im.model;

public interface Session<T> {
    void init(String uid, T t);
    T getConnection();
    String getUid();
    long getLoginAt();
}
