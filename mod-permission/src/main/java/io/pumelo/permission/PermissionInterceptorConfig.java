package io.pumelo.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * author: pumelo
 * 2018/4/20
 */
@Configuration
public class PermissionInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private AbstractPermission abstractPermission;
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(abstractPermission);
    }
}
