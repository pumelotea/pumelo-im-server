package io.pumelo.permission.annotation;

import io.pumelo.common.basebean.PermissionType;

import java.lang.annotation.*;

/**
 * 权限过滤
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface PermissionFilter {
    /**
     * 是否跳过，不加入权限验证
     * @return
     */
    boolean skip() default false;
    /**
     * 仅当 {@link #skip} 为true, 此属性有效，只能应用在Type类型上, 默认过滤所有类型
     * @return
     */
    PermissionType[] filterType() default {PermissionType.MODULE, PermissionType.MENU, PermissionType.FUNCTION};
}
