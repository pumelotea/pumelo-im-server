package io.pumelo.im.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class CorsConfig {


    @Bean
    public FilterRegistrationBean corsFilterBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        SimpleCorsFilter simpleCorsFilter = new SimpleCorsFilter();
        registrationBean.setFilter(simpleCorsFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(1);
        return registrationBean;
    }


}
