package com.tencent.wxcloudrun.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum TaskTriggerEnum {

    PROCEEDING(1, "时间触发"),
    ENDED(2, "条件触发");

    int code;
    String desc;

    TaskTriggerEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
