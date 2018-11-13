package io.pumelo.common.basebean;

public enum MakeStatus {
    PRE_MAKE("待制作"),
    MAKING("制作中"),
    COMPLETE("已完成");
    private String name;

    MakeStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
