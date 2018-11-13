package io.pumelo.aop;

import com.alibaba.fastjson.JSON;
import io.pumelo.common.basebean.CacheKey;
import io.pumelo.common.basebean.RedisCache;
import io.pumelo.redis.ObjectRedis;
import io.pumelo.utils.LOG;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

//@Aspect
//@Order(4)
//@Component
public class AutoCache{
    @Autowired
    private ObjectRedis objectRedis;

    @Pointcut("execution(* io.pumelo..*.*(..))")
    void redisCache(){}

    @Around("redisCache() && @annotation(io.pumelo.common.basebean.RedisCache)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        LOG.info(this,"拦截啦");
        Object object = null;
        Method method = getMethod(joinPoint);
        Object[] args = joinPoint.getArgs();

        RedisCache redisCache = method.getAnnotation(RedisCache.class);
        if(redisCache == null || args.length == 0){
            return joinPoint.proceed();
        }
        switch (redisCache.value()){
            case FIND:
                String keyFind = String.valueOf(args[0].toString());
                object = objectRedis.get(keyFind,method.getReturnType());
                if(object != null){
                    LOG.info(this,"命中缓存:"+ JSON.toJSONString(object));
                    return object;
                }
                object = joinPoint.proceed();
                if(object !=null){
                    objectRedis.add(keyFind,object);
                    LOG.info(this,"新增缓存:"+ JSON.toJSONString(object));
                }
                break;
            case SAVE:
                object = joinPoint.proceed();
                if(object !=null){
                    String keySave = getKeyFromField(object);
                    if(keySave != null){
                        //淘汰已有缓存
                        LOG.info(this,"淘汰缓存:"+ JSON.toJSONString(object));
                        objectRedis.delete(keySave);
                    }
                }
                break;
        }
        return object;
    }

    private String getKeyFromField(Object s){
        Field[] fields = s.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            CacheKey annotation = fields[i].getAnnotation(CacheKey.class);
            if(annotation != null){
                try {
                    fields[i].setAccessible(true);
                    Object o = fields[i].get(s);
                    return String.valueOf(o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

    private Method getMethod(JoinPoint joinPoint) throws Exception {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method;
    }

}
