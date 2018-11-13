package io.pumelo;

import io.pumelo.swagger.annotation.EnableSwagger;
import io.pumelo.utils.jwt.JwtConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@EnableSwagger
@SpringBootApplication
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication webService = new SpringApplication(BackendApplication.class);
        webService.addListeners(new ApplicationEventListener());
        webService.run(args);
    }

    @PostConstruct
    public void init() {
        // 初始化JWT配置
        JwtConstant.readProperties();
    }
}
