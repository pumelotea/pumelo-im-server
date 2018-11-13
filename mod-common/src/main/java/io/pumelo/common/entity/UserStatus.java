package io.pumelo.common.entity;


public enum UserStatus {

    NORMAL("正常状态"),
    DISABLED("禁用状态");

    private final String info;

    private UserStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}