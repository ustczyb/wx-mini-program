package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.constant.TaskTypeConstant;
import com.tencent.wxcloudrun.enums.ProgressStateEnum;
import com.tencent.wxcloudrun.enums.TaskStateEnum;
import com.tencent.wxcloudrun.enums.TaskTriggerEnum;
import com.tencent.wxcloudrun.model.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ConfigController {

    @GetMapping("config/task")
    public ApiResponse getConfig() {
        Map<String, List> configMap = new HashMap<>(2);
        configMap.put("taskType", TaskTypeConstant.getConfig());
        configMap.put("triggerType", Arrays.asList(TaskTriggerEnum.values()));
        configMap.put("progressState", Arrays.asList(ProgressStateEnum.values()));
        configMap.put("taskState", Arrays.asList(TaskStateEnum.values()));
        return ApiResponse.ok(configMap);
    }

}
