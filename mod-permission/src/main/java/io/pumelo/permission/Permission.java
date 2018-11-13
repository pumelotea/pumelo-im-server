package io.pumelo.permission;


/**
 * Permission拦截器接口
 */
public interface Permission {
    boolean isTimeout();

    /**
     * 是否激活权限拦截
     * 默认激活
     * @return
     */
    default boolean isActive(){
      return true;
    }
}
