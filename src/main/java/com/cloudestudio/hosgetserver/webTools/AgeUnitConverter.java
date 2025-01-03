package com.cloudestudio.hosgetserver.webTools;

import java.util.HashMap;
import java.util.Map;

/**
 * @Class AgeUnitConverter
 * @Author Create By Matrix·张
 * @Date 2025/1/3 下午6:02
 * 年龄工具类
 */
public class AgeUnitConverter {
    private static final Map<String, Integer> unitMap = new HashMap<>();
    // 静态代码块用于初始化 Map
    static {
        unitMap.put("岁", 1);
        unitMap.put("月", 2);
        unitMap.put("天",3);
        unitMap.put("时",4);
    }
    // 私有构造函数,防止实例化工具类
    private AgeUnitConverter() {}

    /**
     * 根据给定的键获取对应的值
     *
     * @param unit
     * @return 不存在则返回 null
     */
    public static Integer getValue(String unit) {
        return unitMap.get(unit);
    }
}
