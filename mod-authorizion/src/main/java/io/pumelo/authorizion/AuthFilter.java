package io.pumelo.authorizion;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AuthFilter {
    boolean skip() default false;
}
