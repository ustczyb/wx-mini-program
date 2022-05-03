package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.DO.Progress;
import com.tencent.wxcloudrun.model.DTO.ProgressStatisticDTO;
import com.tencent.wxcloudrun.model.common.ApiResponse;
import com.tencent.wxcloudrun.service.ProgressService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @GetMapping("mission/progress")

    @GetMapping("task/progress")
    public ApiResponse getByTaskAndUser(Long userId, Long taskId) {
        List<Progress> progressList = progressService.queryByUserAndTask(userId, taskId);
        if (CollectionUtils.isNotEmpty(progressList)) {
            return ApiResponse.ok(progressList);
        } else {
            return ApiResponse.error(-1, "query failed");
        }
    }

    @PostMapping("progress/state")
    public ApiResponse modifyState(Long userId, Long taskId, int preState, int targetState) {
        // TODO check mission state if need to modify
        if (progressService.modifyProgressState(userId, taskId, targetState) > 0) {
            return ApiResponse.ok();
        } else {
            return ApiResponse.error(-1, "modify failed");
        }
    }

    @GetMapping("mission/progress/all")

    @GetMapping("task/progress/all")
    public ApiResponse getAllByTaskId(Long taskId) {
        List<Progress> progressList = progressService.queryByTask(taskId);
        if (CollectionUtils.isNotEmpty(progressList)) {
            return ApiResponse.ok(progressList);
        } else {
            return ApiResponse.error(-1, "query failed");
        }
    }

    @PostMapping("mission/progress/end")

    @PostMapping("task/progress/state/end")
    public ApiResponse modifyToEndState(Long taskId) {
        int res = progressService.modifyToEndState(taskId);
        if (res > 0) {
            return ApiResponse.ok();
        } else {
            return ApiResponse.error(-1, "failed");
        }
    }

    @PostMapping("task/progress/state/all")
    public ApiResponse batchModifyState(Long taskId, Integer targetState) {
        if (progressService.modifyAllProgressState(taskId, targetState) > 0) {
            return ApiResponse.ok();
        } else {
            return ApiResponse.error(-1, "modify failed");
        }
    }

    @GetMapping("mission/statistic")

    @GetMapping("task/progress/statistic")
    public ApiResponse getStatisticByTask(Long taskId) {
        ProgressStatisticDTO statisticInfo = progressService.getStatisticInfo(taskId);
        if (statisticInfo != null) {
            return ApiResponse.ok(statisticInfo);
        } else {
            return ApiResponse.error(-1, "query failed");
        }
    }

}
