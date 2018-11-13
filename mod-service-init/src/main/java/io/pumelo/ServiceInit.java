package io.pumelo;

import org.springframework.context.ApplicationEvent;

/**
 * 微服务启动与关闭的时候需要执行的操作接口类
 */
public interface ServiceInit {

    /**
     * 应用已启动完成后的系统操作
     * 暂时包括：
     * 1、初始化权限列表 持久化到DB
     *
     */
    void appStartedOperate(ApplicationEvent event);


    /**
     * 应用关闭后的操作
     * @param event
     */
    void appClosedOperate(ApplicationEvent event);


}
