package io.pumelo.common.basebean;

/**
 * Created by Studio on 2017/11/1.
 */
public enum LibType {
    SELLER("经销商"),
    FIRM("厂商");

    private String name;

    LibType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
