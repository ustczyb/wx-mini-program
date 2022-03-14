package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.DO.Task;
import com.tencent.wxcloudrun.model.DTO.GroupTaskDTO;

import java.util.Date;
import java.util.List;

public interface TaskService {

    int createTask(Task task, List<Long> userIdList);

    int modifyTask(Task task);

    Task queryByTaskId(Long taskId);

    GroupTaskDTO queryByGroupId(Long groupId);

    List<GroupTaskDTO> queryEffectiveTaskByUserId(Long userId);

    List<Task> queryByEndDay(Long userId, Date endDay);

}
