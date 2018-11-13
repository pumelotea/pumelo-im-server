package io.pumelo.common.queue;

import io.pumelo.redis.ObjectRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.lang.reflect.ParameterizedType;

/**
 * Created by Studio on 2017/9/5.
 * Redis封装的队列
 */
@Repository
public class RedisQueue<T> {
    @Autowired
    ObjectRedis objectRedis;

    private String key;

    public RedisQueue() {
    }

    public RedisQueue(String key) {
        this.key = key;
    }

    public void push(T t){
        objectRedis.leftPush(key,t);
    }

    public T pop(){
        Class <T> tClass =  (Class < T > ) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[ 0 ];
        return objectRedis.rightPop(key,tClass);
    }

    public long size(){
        return objectRedis.getListSize(key);
    }
}
