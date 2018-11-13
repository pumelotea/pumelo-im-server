package io.pumelo.db.druid;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DruidConfiguration {

    @Value("${spring.datasource.druid.stat-view-servlet.url-mappings}")
    private String urlMappings;
    @Value("${spring.datasource.druid.stat-view-servlet.url-patterns}")
    private String urlPatterns;
    @Value("${spring.datasource.druid.stat-view-servlet.exclusions}")
    private String exclusions;
    @Value("${spring.datasource.druid.stat-view-servlet.allow}")
    private String allow;
    @Value("${spring.datasource.druid.stat-view-servlet.deny}")
    private String deny;
    @Value("${spring.datasource.druid.stat-view-servlet.login-username}")
    private String loginUsername;
    @Value("${spring.datasource.druid.stat-view-servlet.login-password}")
    private String loginPassword;
    @Value("${spring.datasource.druid.stat-view-servlet.reset-enable}")
    private String resetEnable;

    /**
     * 注册一个StatViewServlet
     * @return
     */
    @Bean
    public ServletRegistrationBean DruidStatViewServle(){
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),urlMappings);

        //添加初始化参数：initParams
        //白名单：
        servletRegistrationBean.addInitParameter("allow",allow);
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny",deny);
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername",loginUsername);
        servletRegistrationBean.addInitParameter("loginPassword",loginPassword);
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable",resetEnable);
        return servletRegistrationBean;
    }

    /**
     * 注册一个：filterRegistrationBean
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns(urlPatterns);
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions",exclusions);
        return filterRegistrationBean;
    }
}
