package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.TaskMapper;
import com.tencent.wxcloudrun.model.DO.Task;
import com.tencent.wxcloudrun.service.TaskService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public int createTask(Task task) {
        Date now = new Date();
        task.setUtime(now);
        task.setCtime(now);
        task.setValid((byte) 1);
        task.setState(1);
        return taskMapper.insert(task);
    }

    @Override
    public int modifyTask(Task task) {
        return taskMapper.updateByPrimaryKeySelective(task);
    }

    @Override
    public Task queryByTaskId(Long taskId) {
        return taskMapper.selectByPrimaryKey(taskId);
    }

    @Override
    public List<Task> queryByGroupId(Long groupId) {
        return taskMapper.queryByGroupIdTasks(groupId);
    }

    @Override
    public List<Task> queryByEndDay(Date endDate) {
        Date date = DateUtils.truncate(endDate, Calendar.DATE);
        return taskMapper.queryByEnddate(date, DateUtils.addDays(date, 1));
    }


}
