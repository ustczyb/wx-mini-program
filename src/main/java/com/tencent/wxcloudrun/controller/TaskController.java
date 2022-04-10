package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.DO.Task;
import com.tencent.wxcloudrun.model.DTO.GroupTaskDTO;
import com.tencent.wxcloudrun.model.common.ApiResponse;
import com.tencent.wxcloudrun.service.TaskService;
import com.tencent.wxcloudrun.utils.ConvertUtils;
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
    public ApiResponse queryByTaskId(Long taskId, Long userId) {
        Task task = taskService.queryByTaskId(taskId, userId);
        if (task != null) {
            return ApiResponse.ok(task);
        } else {
            return ApiResponse.error(-1, "query task failed!");
        }
    }

    @PostMapping("task/task")
    public ApiResponse modifyTaskInfo(Task task, Integer targetState) {
        int modifyRes = taskService.modifyTask(task, targetState);
        if (modifyRes > 0) {
            return ApiResponse.ok();
        } else {
            return ApiResponse.error(-1, "create task failed!");
        }
    }

    @PostMapping("task/access")
    public ApiResponse grantTaskAccess(Long taskId, Long userId, String targetUserIdStr) {
        // TODO
        return ApiResponse.ok();
    }

    @PutMapping("task/task")
    public ApiResponse createTask(Task task, String userIds) {
        int createRes = taskService.createTask(task, ConvertUtils.string2idList(userIds));
        if (createRes > 0) {
            return ApiResponse.ok();
        } else {
            return ApiResponse.error(-1, "create task failed!");
        }
    }

    @GetMapping("group/tasklist")
    public ApiResponse queryByGroupId(Long groupId, Long userId) {
        GroupTaskDTO groupTaskDTO = taskService.queryByGroupId(groupId, userId);
        if (groupTaskDTO != null) {
            return ApiResponse.ok(groupTaskDTO.getTaskList());
        } else {
            return ApiResponse.error(-1, "no result");
        }
    }

    @GetMapping("user/tasklist")
    public ApiResponse queryByUserId(Long userId) {
        List<GroupTaskDTO> taskList = taskService.queryEffectiveTaskByUserId(userId);
        if (CollectionUtils.isNotEmpty(taskList)) {
            return ApiResponse.ok(taskList);
        } else {
            return ApiResponse.error(-1, "no result");
        }
    }

    @GetMapping("user/tasklist/owner")
    public ApiResponse queryByOwnerId(Long userId) {
        List<GroupTaskDTO> taskList = taskService.queryTaskByOwner(userId);
        if (CollectionUtils.isNotEmpty(taskList)) {
            return ApiResponse.ok(taskList);
        } else {
            return ApiResponse.error(-1, "no result");
        }
    }

    @GetMapping("user/tasklist/endtime")
    public ApiResponse queryByTime(Long userId, @DateTimeFormat(pattern = "yyyy-MM")Date endMonth) {
        List<Task> taskList = taskService.queryByMonth(userId, endMonth);
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
