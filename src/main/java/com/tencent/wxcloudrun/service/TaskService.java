package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.DO.Task;
import com.tencent.wxcloudrun.model.DTO.GroupTaskDTO;

import java.util.Date;
import java.util.List;

public interface TaskService {

    int createTask(Task task, List<Long> userIdList);

    int modifyTask(Task task, Integer targetProgressState);

    Task queryByTaskId(Long taskId);

    GroupTaskDTO queryByGroupId(Long groupId, Long userId);

    List<GroupTaskDTO> queryEffectiveTaskByUserId(Long userId);

    List<GroupTaskDTO> queryTaskByOwner(Long userId);

    List<Task> queryByMonth(Long userId, Date endDay);

}
