package io.pumelo.common.basebean;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisCache {
    enum Moment{FIND,SAVE,DELETE}
    Moment value() default Moment.FIND;
}
