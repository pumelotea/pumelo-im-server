package io.pumelo.common.basebean;

public enum OrderStatus {
    IN_PROCESS("处理中"),
    COMPLETE("已完成"),
    CANCEL("作废");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
