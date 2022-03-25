package com.tencent.wxcloudrun.constant;

import com.tencent.wxcloudrun.model.DTO.TaskTypeDTO;

import java.util.*;

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

    private static final List<TaskTypeDTO> CONFIG_LIST = new ArrayList<>();

    static {
        CONFIG_LIST.add(TaskTypeDTO.builder().code(100).desc(TYPE1).subTypeList(
                        Arrays.asList(TaskTypeDTO.builder().code(101).desc(TYPE1_SUBTYPE1).build(),
                                TaskTypeDTO.builder().code(102).desc(TYPE1_SUBTYPE2).build()))
                .build());
        CONFIG_LIST.add(TaskTypeDTO.builder().code(200).desc(TYPE2).subTypeList(
                Arrays.asList(TaskTypeDTO.builder().code(201).desc(TYPE2_SUBTYPE1).build(),
                        TaskTypeDTO.builder().code(202).desc(TYPE2_SUBTYPE2).build())).build());
        CONFIG_LIST.add(TaskTypeDTO.builder().code(200).desc(TYPE2).subTypeList(
                Arrays.asList(TaskTypeDTO.builder().code(301).desc(TYPE3_SUBTYPE1).build(),
                        TaskTypeDTO.builder().code(302).desc(TYPE3_SUBTYPE2).build())).build());
    }

    public static List<TaskTypeDTO> getConfig() {
        return CONFIG_LIST;
    }

}
