package io.pumelo;


import io.pumelo.utils.LOG;
import org.springframework.boot.autoconfigure.jdbc.DataSourceSchemaCreatedEvent;
import org.springframework.boot.context.event.*;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;


/**
 * 在这里可以监听到Spring Boot的生命周期
 */
public class ApplicationEventListener implements ApplicationListener {
    private ServiceInit serverInitService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationStartingEvent){
            LOG.info(this,"========应用开始启动完成========");
        } else if (event instanceof ApplicationEnvironmentPreparedEvent) { // 初始化环境变量
            LOG.info(this,"========初始化环境变量完成========");
        }else if (event instanceof ApplicationPreparedEvent) {
            // 初始化完成 spring boot上下文context创建完成，但此时spring中的bean是没有完全加载完成的
            LOG.info(this,"========上下文context初始化完成========");
        }else if (event instanceof DataSourceSchemaCreatedEvent) { // 数据库初始化完成
            LOG.info(this,"========数据库初始化完成========");

        } else if (event instanceof ContextRefreshedEvent) { // 应用刷新
            LOG.info(this,"========应用刷新完成========");
//        }else if (event instanceof ) {// 初始化应用中的web容器EmbeddedServletContainerInitializedEvent
//            LOG.info(this,"========初始化应用中的web容器完成========");

        }else if (event instanceof ApplicationReadyEvent) {// 应用已启动完成
            LOG.info(this,"========应用已启动完成========");
            ConfigurableApplicationContext applicationContext = ((ApplicationReadyEvent) event).getApplicationContext();
            this.serverInitService = applicationContext.getBean(ServiceInit.class);
//            System.out.println(serverInitService);
            serverInitService.appStartedOperate(event);

        }else if (event instanceof ContextStartedEvent) { // 应用启动，需要在代码动态添加监听器才可捕获（没有出现）
            LOG.info(this,"========应用启动，需要在代码动态添加监听器才可捕获======== ");

        }else if (event instanceof ApplicationFailedEvent) { // 应用启动异常
            LOG.info(this,"========应用启动异常========");

        }else if (event instanceof ContextStoppedEvent) { // 应用停止 (没有出现)
            LOG.info(this,"========应用停止========");

        }else if (event instanceof ContextClosedEvent) { // 应用关闭
            LOG.info(this,"========应用已关闭========");
            serverInitService.appClosedOperate(event);
        }else {
            LOG.info(this,"========event===========："+event);
        }
    }


}
