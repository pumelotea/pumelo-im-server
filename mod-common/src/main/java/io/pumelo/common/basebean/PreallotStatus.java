package io.pumelo.common.basebean;

/**
 * author Pumelo
 * created at 2017/10/27.
 * 预分配状态
 */
public enum PreallotStatus {
    NOT_DELIVER("未交付"),
    DELIVERING("交付中"),
    DELIVER("已交付");

    private String name;

    PreallotStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
