package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.GroupMapper;
import com.tencent.wxcloudrun.dao.ProgressMapper;
import com.tencent.wxcloudrun.dao.TaskMapper;
import com.tencent.wxcloudrun.enums.ProgressStateEnum;
import com.tencent.wxcloudrun.enums.TaskStateEnum;
import com.tencent.wxcloudrun.model.DO.Group;
import com.tencent.wxcloudrun.model.DO.Progress;
import com.tencent.wxcloudrun.model.DO.Task;
import com.tencent.wxcloudrun.model.DTO.GroupTaskDTO;
import com.tencent.wxcloudrun.service.TaskService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ProgressMapper progressMapper;

    @Autowired
    private GroupMapper groupMapper;

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
        task.setState(TaskStateEnum.NOT_BEGIN.getCode());
        return task;
    }

    private Progress buildProgress(Task task, Long userId) {
        Date now = new Date();
        return Progress.builder()
                .groupId(task.getGroupId())
                .taskId(task.getTaskId())
                .userId(userId)
                .state((short) ProgressStateEnum.TO_READ.getCode())
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
    public GroupTaskDTO queryByGroupId(Long groupId) {
        Group group = groupMapper.selectByPrimaryKey(groupId);
        List<Task> taskList = taskMapper.queryByGroupIdTasks(groupId);
        return GroupTaskDTO.builder().group(group).taskList(taskList).build();
    }

    @Override
    public List<GroupTaskDTO> queryEffectiveTaskByUserId(Long userId) {
        // 1.find progresses of user
        List<Progress> progressList = progressMapper.selectByUserIdProgressList(userId);
        List<Long> taskIdList = progressList.stream().map(Progress::getTaskId).collect(Collectors.toList());
        // 2.find corresponding tasks
        List<Task> taskList = taskMapper.selectByIds(taskIdList);
        Map<Long, List<Task>> groupTaskMap = taskList.stream().filter(x -> x.getExpectFinishedTime().after(new Date())).collect(Collectors.groupingBy(Task::getGroupId));
        return groupTaskMap.entrySet().stream().map(e -> buildGroupTaskDTO(e.getKey(), e.getValue())).collect(Collectors.toList());
    }

    @Override
    public List<GroupTaskDTO> queryTaskByOwner(Long userId) {
        List<Task> taskList = taskMapper.selectByCreateUserIdTasks(userId);
        Map<Long, List<Task>> groupTaskMap = taskList.stream().filter(x -> x.getExpectFinishedTime().after(new Date())).collect(Collectors.groupingBy(Task::getGroupId));
        return groupTaskMap.entrySet().stream().map(e -> buildGroupTaskDTO(e.getKey(), e.getValue())).collect(Collectors.toList());
    }

    private GroupTaskDTO buildGroupTaskDTO(Long groupId, List<Task> tasks) {
        Group group = groupMapper.selectByPrimaryKey(groupId);
        return GroupTaskDTO.builder().group(group).taskList(tasks).build();
    }

    @Override
    public List<Task> queryByMonth(Long userId, Date endDate) {
        // 1.find progresses of user
        List<Progress> progressList = progressMapper.selectByUserIdProgressList(userId);
        List<Long> taskIdList = progressList.stream().map(Progress::getTaskId).collect(Collectors.toList());
        // 2.find corresponding tasks
        List<Task> taskList = taskMapper.selectByIds(taskIdList);
        taskList = taskList.stream()
                .filter(x -> x.getExpectFinishedTime().after(DateUtils.truncate(endDate, Calendar.MONTH)))
                .filter(x -> x.getExpectFinishedTime().before(DateUtils.addMonths(DateUtils.truncate(endDate, Calendar.MONTH), 1)))
                .collect(Collectors.toList());
        return taskList;
    }


}
