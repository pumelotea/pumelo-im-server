package io.pumelo.permission.annotation;

import io.pumelo.common.basebean.PermissionType;

import java.lang.annotation.*;

/**
 * 菜单权限 注释，提供权限过滤，菜单生成策略
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionRegister {

    /**
     *  菜单id
     * @return
     */
    String key();

    /**
     * 菜单名称
     * @return
     */
    String name();

    /**
     * 菜单类型
     * @return
     */
    PermissionType type();

    /**
     * 父菜单Id, module时为空，否则不为空
     * @return
     */
    String parentKey() default "";

    /**
     * 映射路径
     * @return
     */
    String mappingUrl() default "";

    /**
     * 排序号
     * @return
     */
    int sortNum() default 0;
}
