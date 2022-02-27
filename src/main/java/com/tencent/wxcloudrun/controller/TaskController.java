package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public class TaskController {

    @GetMapping("task/task")
    public ApiResponse queryByTaskId(Long taskId) {
        return ApiResponse.ok();
    }

    @PostMapping("task/task")
    public ApiResponse modifyTaskInfo() {
        return ApiResponse.ok();
    }

    @PutMapping("task/task")
    public ApiResponse createTask() {
        return ApiResponse.ok();
    }

    @GetMapping("task/group")
    public ApiResponse queryByGroupId(Long groupId) {
        return ApiResponse.ok();
    }

    @GetMapping("task/time")
    public ApiResponse queryByTime() {
        return ApiResponse.ok();
    }


}
