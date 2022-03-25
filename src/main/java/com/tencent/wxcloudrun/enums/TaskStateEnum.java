package com.tencent.wxcloudrun.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum TaskStateEnum {

    NOT_BEGIN(100, "未开始"),
    PROCEEDING(200, "进行中"),
    ENDED(300, "已结束");

    int code;
    String desc;

    TaskStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static final Map<Integer, TaskStateEnum> stateEnumMap;

    static {
        stateEnumMap = new HashMap<>(7);
        for (TaskStateEnum stateEnum: TaskStateEnum.values()) {
            stateEnumMap.put(stateEnum.code, stateEnum);
        }
    }

    public static TaskStateEnum codeOf(int code) {
        return stateEnumMap.get(code);
    }
}
