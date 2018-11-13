package io.pumelo.common.basebean;

public enum PayStatus {
    NOT_PAY("未支付"),
    PAID("已支付");


    private String name;
    PayStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
