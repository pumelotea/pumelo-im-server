package io.pumelo;

import io.pumelo.idgens.annotation.EnableIdGens;
import io.pumelo.im.GoogleAuthenticator;
import io.pumelo.utils.jwt.JwtConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableIdGens
@RestController
public class PumeloImApplication {

    @Autowired
    private GoogleAuthenticator googleAuthenticator;

    private String secret;
    @PostConstruct
    public void init() {
        // 初始化JWT配置
        JwtConstant.readProperties();
        googleAuthenticator.setWindowSize(1);
        secret = GoogleAuthenticator.generateSecretKey();
    }

    public static void main(String[] args) {
        SpringApplication webService = new SpringApplication(PumeloImApplication.class);
        webService.addListeners(new ApplicationEventListener());
        webService.run(args);
    }



    @GetMapping("/test1")
    public String test(){
        return GoogleAuthenticator.getQRBarcodeURL("zhufeng","127.0.0.1",secret);
    }

    @GetMapping("/test2")
    public boolean cj(long code){
       return googleAuthenticator.checkCode(secret,code,System.currentTimeMillis());
    }
}
