package io.pumelo.common.basebean;

/**
 * Created by Studio on 2017/11/8.
 */
public enum CommodityPriceStatus {
    NOT_SHELVES("未上架"),
    SHELVES("已上架");

    private String name;

    CommodityPriceStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
