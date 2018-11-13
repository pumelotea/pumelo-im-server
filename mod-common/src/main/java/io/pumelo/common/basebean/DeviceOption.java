package io.pumelo.common.basebean;

public enum DeviceOption {
    ID_A("id_a"),
    ID_R("id_r"),
    HEARTBEAT_A("heartbeat_a"),
    HEARTBEAT_R("heartbeat_r"),
    QRCODE_A("qrcode_a"),
    QRCODE_C("qrcode_c"),
    QRCODE_R("qrcode_r"),
    CTRL_A("ctrl_a"),
    CTRL_R("ctrl_r"),
    STATE_A("state_a"),
    STATE_R("state_r"),
    CONFIG_A("config_a"),
    CONFIG_R("config_r");

    private String name;

    DeviceOption(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
