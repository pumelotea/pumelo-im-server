package io.pumelo;

import io.pumelo.utils.jwt.JwtConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class PumeloImApplication {

    @PostConstruct
    public void init() {
        // 初始化JWT配置
        JwtConstant.readProperties();
    }
    /**
     * ws配置
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    public static void main(String[] args) {
        SpringApplication.run(PumeloImApplication.class, args);
    }
}
