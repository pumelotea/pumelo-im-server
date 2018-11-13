package io.pumelo.common.basebean;

/**
 * Created by Studio on 2017/11/8.
 */
public enum  CfgTypeEnum {
    CFG_TYPE("goods");

    private String name;

    CfgTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
