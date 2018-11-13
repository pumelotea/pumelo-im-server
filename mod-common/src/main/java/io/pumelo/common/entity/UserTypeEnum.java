package io.pumelo.common.entity;

/**
 * 用户类型
 */
public enum UserTypeEnum {
    /**
     * 内部账户(系统帐号)
     */
    INTERNAL,
    /**
     * 某一智能硬件企业的内部系统管理员账号
     * 由该企业的内部管理人员自行创建(下级帐号，管理员帐号)
     */
    BACKSTAGE,
    /**
     * 普通账户(平级帐号)
     */
    NORMAL
}
