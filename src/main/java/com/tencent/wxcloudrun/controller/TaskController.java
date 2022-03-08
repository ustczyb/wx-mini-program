package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.DO.Task;
import com.tencent.wxcloudrun.model.common.ApiResponse;
import com.tencent.wxcloudrun.service.TaskService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("task/task")
    public ApiResponse queryByTaskId(Long taskId) {
        Task task = taskService.queryByTaskId(taskId);
        if (task != null) {
            return ApiResponse.ok(task);
        } else {
            return ApiResponse.error(-1, "query task failed!");
        }
    }

    @PostMapping("task/task")
    public ApiResponse modifyTaskInfo(Task task) {
        int createRes = taskService.modifyTask(task);
        if (createRes > 0) {
            return ApiResponse.ok();
        } else {
            return ApiResponse.error(-1, "create task failed!");
        }
    }

    @PutMapping("task/task")
    public ApiResponse createTask(Task task) {
        int createRes = taskService.createTask(task);
        if (createRes > 0) {
            return ApiResponse.ok();
        } else {
            return ApiResponse.error(-1, "create task failed!");
        }
    }

    @GetMapping("task/group")
    public ApiResponse queryByGroupId(Long groupId) {
        List<Task> taskList = taskService.queryByGroupId(groupId);
        if (CollectionUtils.isNotEmpty(taskList)) {
            return ApiResponse.ok(taskList);
        } else {
            return ApiResponse.error(-1, "query failed!");
        }
    }

    @GetMapping("task/time")
    public ApiResponse queryByTime(@DateTimeFormat(pattern = "yyyy-MM-dd")Date endDate) {
        List<Task> taskList = taskService.queryByEndDay(endDate);
        if (CollectionUtils.isNotEmpty(taskList)) {
            return ApiResponse.ok(taskList);
        } else {
            return ApiResponse.error(-1, "query failed!");
        }
    }

    @GetMapping("task/messageboard")
    public ApiResponse queryMessageboardByGroupId(Long taskId, int pageNum, int pageSize) {
        return ApiResponse.ok();
    }

    @PutMapping("task/messageboard")
    public ApiResponse createMessage(Long taskId, Long userId, String message) {
        return ApiResponse.ok();
    }


}
