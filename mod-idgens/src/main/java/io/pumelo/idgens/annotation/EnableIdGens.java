package io.pumelo.idgens.annotation;

import io.pumelo.idgens.worker.IdWorker;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by Pumelo.
 * Mail: 535553297@qq.com
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({IdWorker.class})
@Documented
public @interface EnableIdGens {
}
