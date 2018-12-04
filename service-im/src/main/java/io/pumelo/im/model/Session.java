package io.pumelo.im.model;

/**
 * 基本长连接会话接口
 * @param <T>
 */
public interface Session<T> {
    void init(String uid, T t);
    T getConnection();
    String getUid();
    long getLoginAt();
}
