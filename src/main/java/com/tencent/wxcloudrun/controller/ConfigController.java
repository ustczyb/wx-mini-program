package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.constant.TaskTriggerTypeConstant;
import com.tencent.wxcloudrun.constant.TaskTypeConstant;
import com.tencent.wxcloudrun.model.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ConfigController {

    @GetMapping("config/task")
    public ApiResponse getConfig() {
        Map<String, Map> configMap = new HashMap<>(2);
        configMap.put("taskType", TaskTypeConstant.getConfig());
        configMap.put("triggerType", TaskTriggerTypeConstant.getConfig());
        return ApiResponse.ok(configMap);
    }

    @GetMapping("config/task/type")
    public ApiResponse getTaskType() {
        return ApiResponse.ok(TaskTypeConstant.getConfig());
    }

    @GetMapping("config/task/triggertype")
    public ApiResponse getTaskTriggerType() {
        return ApiResponse.ok(TaskTriggerTypeConstant.getConfig());
    }
}
