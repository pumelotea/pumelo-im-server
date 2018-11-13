package io.pumelo.utils.annotation;

import java.lang.annotation.*;


@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogExeTime {

    String description() default "";

    boolean log() default true;

    boolean exeTime() default true;

    MVCType mvcType() default MVCType.CONTROLLER;
}
