package io.pumelo.common.basebean;

/**
 * Created by Studio on 2017/11/8.
 */
public enum GoodsConfigEnum {
    TEMP("temperature"),
    COFFEE("singleBean"),
    WATER("singleWater"),
    MILK("singleMilk"),
    SEC_MAKE("makeTime"),
    NUM_MAKE("makeNumber"),
    WATER_TOTAL("availableWater"),
    MILK_TOTAL("availableMilk"),
    COFFEE_TOTAL("availableBean"),
    SEC_MILK("milkTime"),
    SEC_COFFEE("beanTime");

    private String name;

    GoodsConfigEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
