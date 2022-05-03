package com.tencent.wxcloudrun.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum MissionProgressStateEnum {

    TO_READ(101, "未读"),
    HAVE_READ(102, "已读"),
    COMPLETED(201, "已处理"),
    ENDED(301, "已结束");

    int code;
    String desc;

    MissionProgressStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public boolean isFinalState() {
        return this == ENDED;
    }

    private static final Map<Integer, MissionProgressStateEnum> stateEnumMap;

    static {
        stateEnumMap = new HashMap<>(MissionProgressStateEnum.values().length);
        for (MissionProgressStateEnum stateEnum: MissionProgressStateEnum.values()) {
            stateEnumMap.put(stateEnum.code, stateEnum);
        }
    }

    public static MissionProgressStateEnum codeOf(int code) {
        return stateEnumMap.get(code);
    }

}
