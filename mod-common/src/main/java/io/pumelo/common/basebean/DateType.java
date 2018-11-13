package io.pumelo.common.basebean;


public enum DateType {
    DAY("DAY"),
    MONTH("MONTH"),
    YEAR("YEAR");
    private final String info;
    private DateType(String info) {
        this.info = info;
    }
    public  String getName() {
        return info;
    }
}
