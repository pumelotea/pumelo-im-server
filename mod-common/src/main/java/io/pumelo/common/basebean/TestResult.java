package io.pumelo.common.basebean;

/**
 * Created by Fyb
 * on 2017/11/1.
 */
public enum TestResult {
    PRE_TEST("待测试"),
    PASSED("通过"),
    NOT_PASS("不通过");

    private String name;

    TestResult(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
