package io.pumelo;

import io.pumelo.idgens.annotation.EnableIdGens;
import io.pumelo.utils.jwt.JwtConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableIdGens
public class PumeloImApplication {


    @PostConstruct
    public void init() {
        // 初始化JWT配置
        JwtConstant.readProperties();
    }

    public static void main(String[] args) {
        SpringApplication webService = new SpringApplication(PumeloImApplication.class);
        webService.addListeners(new ApplicationEventListener());
        webService.run(args);
    }
}
