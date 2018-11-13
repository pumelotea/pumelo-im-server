package io.pumelo.authorizion;

/**
 * 获取token携带的用户唯一标示的接口
 * @param <T>
 */
public interface IdGetter<T> {
    T getId();
}
