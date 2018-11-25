package io.pumelo.im.initializer;

import io.pumelo.ServiceInit;
import io.pumelo.data.im.entity.UserEntity;
import io.pumelo.data.im.repo.UserEntityRepo;
import io.pumelo.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * 服务启动事件监听器
 */
@Component
public class SystemInit implements ServiceInit {

    private ConfigurableApplicationContext applicationContext;
    @Autowired
    private UserEntityRepo userEntityRepo;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void appStartedOperate(ApplicationEvent event) {
        this.applicationContext = ((ApplicationReadyEvent) event).getApplicationContext();
        this.initUserIdPool();

    }

    @Override
    public void appClosedOperate(ApplicationEvent event) {
        LOG.info(this,"process is shutdown");
    }

    /**
     * 初始化用户ID池
     */
    private void initUserIdPool(){
        List<String> uidList = userEntityRepo.findUidList();
        Set<String> uidSet = new HashSet<>();
        uidList.forEach(uid-> uidSet.add(uid));
        List<String> ids = new ArrayList<>();
        //开放id 2000000 - 3000000
        for(int i=2000000;i<3000000;i++){
            boolean contains = uidSet.contains(i);
            if (!contains) {
                ids.add(String.valueOf(i));
            }
        }
        redisTemplate.delete("UserIdPool");
        redisTemplate.opsForSet().add("UserIdPool",ids.toArray(new String[ids.size()]));
        LOG.info(this,"UserIdPool init success");
    }
}
