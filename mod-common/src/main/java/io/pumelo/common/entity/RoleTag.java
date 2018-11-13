package io.pumelo.common.entity;


public enum RoleTag {
    COMMON_ROLE("普通人员（人为创建）"),
    MANAGE_ROLE("管理员（人为创建）"),
    ROOT_ROLE("顶级管理员（系统自动创建，拥有所有权限）");

    private final String info;

    RoleTag(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
