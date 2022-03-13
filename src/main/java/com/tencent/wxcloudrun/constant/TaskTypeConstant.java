package com.tencent.wxcloudrun.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskTypeConstant {

    public static final String TYPE1 = "通知类型";
    public static final String TYPE2 = "参与类型";
    public static final String TYPE3 = "作业类型";

    public static final String TYPE1_SUBTYPE1 = "通知";
    public static final String TYPE1_SUBTYPE2 = "通知并确认";

    public static final String TYPE2_SUBTYPE1 = "二维码签到";
    public static final String TYPE2_SUBTYPE2 = "定位签到";

    public static final String TYPE3_SUBTYPE1 = "不需要上传材料";
    public static final String TYPE3_SUBTYPE2 = "需要上传材料";

    private static final Map<String, Integer> CONFIG_MAP = new HashMap<>();

    static {
        CONFIG_MAP.put(TYPE1, 100);
        CONFIG_MAP.put(TYPE1_SUBTYPE1, 101);
        CONFIG_MAP.put(TYPE1_SUBTYPE2, 102);
        CONFIG_MAP.put(TYPE2, 200);
        CONFIG_MAP.put(TYPE2_SUBTYPE1, 201);
        CONFIG_MAP.put(TYPE2_SUBTYPE2, 202);
        CONFIG_MAP.put(TYPE3, 300);
        CONFIG_MAP.put(TYPE3_SUBTYPE1, 301);
        CONFIG_MAP.put(TYPE3_SUBTYPE2, 302);
    }

    public static Map<String, Integer> getConfig() {
        return CONFIG_MAP;
    }

}
