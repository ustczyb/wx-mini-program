package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.DO.Task;

import java.util.Date;
import java.util.List;

public interface TaskService {

    int createTask(Task task, List<Long> userIdList);

    int modifyTask(Task task);

    Task queryByTaskId(Long taskId);

    List<Task> queryByGroupId(Long groupId);

    List<Task> queryByEndDay(Date endDay);

}
