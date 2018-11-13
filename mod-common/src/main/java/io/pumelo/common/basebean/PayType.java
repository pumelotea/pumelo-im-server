package io.pumelo.common.basebean;

/**
 * author Pumelo
 * created at 2017/10/31.
 */
public enum PayType {
    WXPAY("微信"),
    ALIPAY("支付宝"),
    CARD("卡券");

    private String name;

    PayType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
