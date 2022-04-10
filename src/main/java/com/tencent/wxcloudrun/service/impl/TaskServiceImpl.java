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
import com.tencent.wxcloudrun.model.DTO.TaskProgressDTO;
import com.tencent.wxcloudrun.service.TaskService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    @Transactional(rollbackFor = {Exception.class})
    public int createTask(Task task, List<Long> userIdList) {
        Task newTask = buildTask(task);
        int insertTaskRes = taskMapper.insertSelective(newTask);
        for (Long userId : userIdList) {
            Progress progress = buildProgress(newTask, userId);
            progressMapper.insertSelective(progress);
        }
        return insertTaskRes;
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
    @Transactional(rollbackFor = Exception.class)
    public int modifyTask(Task task, Integer targetProgressState) {
        Task originTask = taskMapper.selectByPrimaryKey(task.getTaskId());
        if (Objects.equals(originTask.getState(), TaskStateEnum.ENDED.getCode())) {
            LOGGER.warn("task {} has been ended.", originTask.getTaskId());
            return -1;
        }
        int modifyStatus = taskMapper.updateByPrimaryKeySelective(task);
        if (targetProgressState != null) {
            return progressMapper.updateStateByUserIdAndTaskId(null, task.getTaskId(), null, targetProgressState);
        } else {
            return modifyStatus;
        }
    }

    @Override
    public Task queryByTaskId(Long taskId) {
        return taskMapper.selectByPrimaryKey(taskId);
    }

    @Override
    public GroupTaskDTO queryByGroupId(Long groupId, Long userId) {
        List<Task> taskList = taskMapper.queryByGroupIdTasks(groupId);
        return buildGroupTaskDTO(groupId, userId, taskList);
    }

    private Map<String, Object> buildParamMap(Long userId, List<Long> taskIdList) {
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("userId", userId);
        paramMap.put("taskIds", taskIdList);
        return paramMap;
    }

    @Override
    public List<GroupTaskDTO> queryEffectiveTaskByUserId(Long userId) {
        // 1.find progresses of user
        List<Progress> progressList = progressMapper.selectByUserIdProgressList(userId);
        List<Long> taskIdList = progressList.stream().map(Progress::getTaskId).collect(Collectors.toList());
        // 2.find corresponding tasks
        List<Task> taskList = taskMapper.selectByIds(taskIdList).stream()
                .filter(p -> !Objects.equals(p.getState(), TaskStateEnum.ENDED.getCode()))
                .sorted(Comparator.comparing(Task::getExpectFinishedTime))
                .collect(Collectors.toList());
        Map<Long, List<Task>> groupTaskMap = taskList.stream().filter(x -> x.getExpectFinishedTime().after(new Date())).collect(Collectors.groupingBy(Task::getGroupId));
        return groupTaskMap.entrySet().stream().map(e -> buildGroupTaskDTO(e.getKey(), userId, e.getValue())).collect(Collectors.toList());
    }

    @Override
    public List<GroupTaskDTO> queryTaskByOwner(Long userId) {
        List<Task> taskList = taskMapper.selectByCreateUserIdTasks(userId).stream()
                .filter(p -> !Objects.equals(p.getState(), TaskStateEnum.ENDED.getCode()))
                .sorted(Comparator.comparing(Task::getExpectFinishedTime))
                .collect(Collectors.toList());
        Map<Long, List<Task>> groupTaskMap = taskList.stream().filter(x -> x.getExpectFinishedTime().after(new Date())).collect(Collectors.groupingBy(Task::getGroupId));
        return groupTaskMap.entrySet().stream().map(e -> buildGroupTaskDTO(e.getKey(), userId, e.getValue())).collect(Collectors.toList());
    }

    private GroupTaskDTO buildGroupTaskDTO(Long groupId, Long userId, List<Task> tasks) {
        Group group = groupMapper.selectByPrimaryKey(groupId);
        List<Long> taskIdList = tasks.stream().map(Task::getTaskId).collect(Collectors.toList());
        Map<Long, List<Progress>> progressMap = progressMapper.selectByUserIdAndTaskIds(buildParamMap(userId, taskIdList)).stream().collect(Collectors.groupingBy(Progress::getTaskId));
        List<TaskProgressDTO> taskProgressDTOList = tasks.stream()
                .filter(t -> progressMap.get(t.getTaskId()) != null)
                .map(t -> TaskProgressDTO.builder()
                        .task(t)
                        .progress(progressMap.get(t.getTaskId()).get(0))
                        .build())
                .collect(Collectors.toList());
        return GroupTaskDTO.builder().group(group).taskList(taskProgressDTOList).build();
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
