package io.pumelo.common.basebean;

/**
 * Created by Studio on 2017/11/1.
 */
public enum PriceType {
    DEFAULT("默认"),
    RULE("规则");
    private String name;

    PriceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
