package com.tencent.wxcloudrun.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ProgressStateEnum {

    TO_READ(101, "未读"),
    HAVE_READ(102, "已读"),
    COMPLETED(201, "已完成"),
    CHECKED(202, "已审批"),
    REJECTED(203, "已拒绝"),
    FINISHED_WITH_CHECKED(301, "已结束(已审批)"),
    FINISHED_WITHOUT_CHECKED(302, "已结束(未审批)"),
    EXPIRED(303, "已过期");

    int code;
    String desc;

    ProgressStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public boolean isFinalState() {
        return this == FINISHED_WITH_CHECKED || this == FINISHED_WITHOUT_CHECKED || this == EXPIRED;
    }

    private static final Map<Integer, ProgressStateEnum> stateEnumMap;

    static {
        stateEnumMap = new HashMap<>(ProgressStateEnum.values().length);
        for (ProgressStateEnum stateEnum: ProgressStateEnum.values()) {
            stateEnumMap.put(stateEnum.code, stateEnum);
        }
    }

    public static ProgressStateEnum codeOf(int code) {
        return stateEnumMap.get(code);
    }
}
