package io.pumelo.common.basebean;

/**
 * Created by Fyb
 * on 2017/11/1.
 */
public enum DeviceStatus {
    RUN("运行"),
    POWEROFF("关机"),
    BREAKDOWN("故障");

    private String name;

    DeviceStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
