package io.pumelo.common.basebean;

/**
 * author Pumelo
 * created at 2017/10/27.
 * 字典表key
 */
public enum DictionaryKey {
    PRODUCT_TYPE("product_type"),
    COMMODITY_LIB_TYPE("commodity_lib_type"),
    H5_ORDER_QRCODE_URL("h5_order_qrcode_url"),//h5二维码url
    COMMODITY_TYPE("commodity_type");

    private String name;

    DictionaryKey(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
