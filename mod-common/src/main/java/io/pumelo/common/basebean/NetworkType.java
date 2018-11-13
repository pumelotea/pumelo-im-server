package io.pumelo.common.basebean;

/**
 * Created by Fyb
 * on 2017/11/1.
 */
public enum NetworkType {
    GPRS("GPRS在线"),
    WIFI("WIFI在线"),
    OFFLINE("离线");

    private String name;

    NetworkType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
