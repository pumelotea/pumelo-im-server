package io.pumelo.authorizion;

/**
 * 获取数据对象的接口
 * @param <T>
 */
public interface ModelGetter<T> {
    T getModel();
}
