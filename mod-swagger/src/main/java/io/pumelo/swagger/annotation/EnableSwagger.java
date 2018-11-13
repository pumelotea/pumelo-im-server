package io.pumelo.swagger.annotation;

import io.pumelo.swagger.SwaggerConfiguration;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.*;

/**
 * Created by Pumelo.
 * Mail: 535553297@qq.com
 */
@EnableSwagger2
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({SwaggerConfiguration.class})
@Documented
public @interface EnableSwagger {
}
