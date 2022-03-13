package com.tencent.wxcloudrun.constant;

import java.util.HashMap;
import java.util.Map;

public class TaskTriggerTypeConstant {

    public static final String TYPE1 = "时间触发";
    public static final String TYPE2 = "条件触发";

    private static final Map<String, Integer> CONFIG_MAP = new HashMap<>();

    static {
        CONFIG_MAP.put(TYPE1, 1);
        CONFIG_MAP.put(TYPE2, 2);
    }

    public static Map<String, Integer> getConfig() {
        return CONFIG_MAP;
    }

}
