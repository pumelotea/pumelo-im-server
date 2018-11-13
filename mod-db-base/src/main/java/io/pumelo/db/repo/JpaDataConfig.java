package io.pumelo.db.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * 注解配置jpa factory-class
 */
@Configuration
@EnableJpaRepositories(basePackages = "io.pumelo.**.repo", repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
@EnableSpringDataWebSupport
public class JpaDataConfig {
}
