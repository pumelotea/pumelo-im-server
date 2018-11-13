package io.pumelo.common.basebean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 性别枚举
 * 与PAAS平台的枚举命名保持一致
 */
public enum Gender {
    /**
     * 未知性别
     */
    N,
    /**
     * 男性
     */
    M,
    /**
     *女性
     */
    F;

    public static List<String> genderNames(){
        Gender[] values = Gender.values();
        List<String> names = new ArrayList<>();
        Arrays.stream(values).forEachOrdered(value->{
            names.add(value.name());
        });
        return names;
    }

}
