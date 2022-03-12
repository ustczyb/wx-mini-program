package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.ProgressMapper;
import com.tencent.wxcloudrun.dao.TaskMapper;
import com.tencent.wxcloudrun.model.DO.Progress;
import com.tencent.wxcloudrun.model.DO.Task;
import com.tencent.wxcloudrun.service.TaskService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ProgressMapper progressMapper;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public int createTask(Task task, List<Long> userIdList) {
        Task newTask = buildTask(task);
        int insertTaskRes = taskMapper.insertSelective(task);
        for (Long userId : userIdList) {
            Progress progress = buildProgress(task, userId);
            progressMapper.insertSelective(progress);
        }
        return 1;
    }

    private Task buildTask(Task task) {
        Date now = new Date();
        task.setUtime(now);
        task.setCtime(now);
        task.setValid((byte) 1);
        task.setState(1);
        return task;
    }

    private Progress buildProgress(Task task, Long userId) {
        Date now = new Date();
        return Progress.builder()
                .groupId(task.getGroupId())
                .taskId(task.getId())
                .userId(userId)
                .state((byte) 1)
                .ctime(now)
                .utime(now)
                .build();
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
