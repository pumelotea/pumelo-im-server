package io.pumelo;

import io.pumelo.idgens.annotation.EnableIdGens;
import io.pumelo.idgens.worker.IdWorker;
import io.pumelo.im.IMContext;
import io.pumelo.im.Processtor.MySQLPersistentProcessor;
import io.pumelo.utils.jwt.JwtConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableIdGens
public class PumeloImApplication {

    @Autowired
    private MySQLPersistentProcessor persistentProcessor;
    @Autowired
    private IdWorker idWorker;

    @PostConstruct
    public void init() {
        // 初始化JWT配置
        JwtConstant.readProperties();
        // 设置持久化处理器
        IMContext.setPersistentProcessor(persistentProcessor);
        IMContext.setIdWorker(idWorker);
    }

    public static void main(String[] args) {
        SpringApplication webService = new SpringApplication(PumeloImApplication.class);
        webService.addListeners(new ApplicationEventListener());
        webService.run(args);
    }
}
