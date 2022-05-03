package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.*;
import com.tencent.wxcloudrun.enums.MissionProgressStateEnum;
import com.tencent.wxcloudrun.enums.ProgressStateEnum;
import com.tencent.wxcloudrun.enums.TaskStateEnum;
import com.tencent.wxcloudrun.model.DO.*;
import com.tencent.wxcloudrun.model.DTO.GroupMissionDTO;
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

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ProgressMapper progressMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private MissionMapper missionMapper;

    @Autowired
    private MissionProgressMapper missionProgressMapper;

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
    public Task queryByTaskId(Long taskId, Long userId) {
        progressMapper.updateLastViewTime(userId, taskId);
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

    @Override
    public Mission queryByMissionId(Long missionId, Long userId) {
        missionProgressMapper.updateLastViewTime(userId, missionId);
        return missionMapper.selectByPrimaryKey(missionId);
    }

    @Override
    public int modifyMission(Mission mission) {
        return missionMapper.updateByPrimaryKeySelective(mission);
    }

    private Mission buildMission(Mission mission) {
        Date now = new Date();
        mission.setUtime(now);
        mission.setCtime(now);
        mission.setValid((byte) 1);
        mission.setActualStartTime(now);
        mission.setState(TaskStateEnum.NOT_BEGIN.getCode());
        return mission;
    }

    private MissionProgress buildMissionProgress(Mission mission, Long userId) {
        Date now = new Date();
        return MissionProgress.builder()
                .groupId(mission.getGroupId())
                .missionId(mission.getId())
                .lastViewTime(now)
                .userId(userId)
                .state((short) ProgressStateEnum.TO_READ.getCode())
                .ctime(now)
                .utime(now)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createMission(Mission mission, List<Long> userIds) {
        Mission newMission = buildMission(mission);
        int insertTaskRes = missionMapper.insertSelective(newMission);
        for (Long userId : userIds) {
            MissionProgress progress = buildMissionProgress(newMission, userId);
            missionProgressMapper.insertSelective(progress);
        }
        return insertTaskRes;
    }

    private GroupMissionDTO buildGroupMissionDTO(Long groupId, Long userId, List<Mission> missions) {
        Group group = groupMapper.selectByPrimaryKey(groupId);
        List<Long> missionIdList = missions.stream().map(Mission::getId).collect(Collectors.toList());
        Map<Long, List<MissionProgress>> progressMap = missionProgressMapper.selectByUserIdAndTaskIds(buildParamMap(userId, missionIdList)).stream().collect(Collectors.groupingBy(MissionProgress::getMissionId));
        List<GroupMissionDTO.MissionProgressDTO> taskProgressDTOList = missions.stream()
                .filter(t -> progressMap.get(t.getId()) != null)
                .map(t -> GroupMissionDTO.MissionProgressDTO.builder()
                        .mission(t)
                        .progress(progressMap.get(t.getId()).get(0))
                        .build())
                .collect(Collectors.toList());
        return GroupMissionDTO.builder().group(group).taskList(taskProgressDTOList).build();
    }

    @Override
    public List<GroupMissionDTO> queryEffectiveMissionByUserId(Long userId) {
        // 1.find progresses of user
        List<MissionProgress> progressList = missionProgressMapper.selectByUserIdProgressList(userId);
        List<Long> missionIdList = progressList.stream().map(MissionProgress::getMissionId).collect(Collectors.toList());
        // 2.find corresponding tasks
        List<Mission> missionList = missionMapper.selectByIds(missionIdList).stream()
                .filter(p -> !Objects.equals(p.getState(), TaskStateEnum.ENDED.getCode()))
                .sorted(Comparator.comparing(Mission::getActualStartTime))
                .collect(Collectors.toList());
        Map<Long, List<Mission>> groupTaskMap = missionList.stream().filter(x -> x.getState() < MissionProgressStateEnum.ENDED.getCode()).collect(Collectors.groupingBy(Mission::getGroupId));
        return groupTaskMap.entrySet().stream().map(e -> buildGroupMissionDTO(e.getKey(), userId, e.getValue())).collect(Collectors.toList());

    }

    @Override
    public List<GroupMissionDTO> queryMissionByOwner(Long userId) {
        List<Mission> missionList = missionMapper.selectByCreateUserIdTasks(userId).stream()
                .filter(p -> !Objects.equals(p.getState(), TaskStateEnum.ENDED.getCode()))
                .sorted(Comparator.comparing(Mission::getActualStartTime))
                .collect(Collectors.toList());
        Map<Long, List<Mission>> groupTaskMap = missionList.stream().filter(x -> x.getState() < MissionProgressStateEnum.ENDED.getCode()).collect(Collectors.groupingBy(Mission::getGroupId));
        return groupTaskMap.entrySet().stream().map(e -> buildGroupMissionDTO(e.getKey(), userId, e.getValue())).collect(Collectors.toList());

    }

    @Override
    public List<Mission> queryMissionByMonth(Long userId, Date endDate) {
        // 1.find progresses of user
        List<MissionProgress> progressList = missionProgressMapper.selectByUserIdProgressList(userId);
        List<Long> missionIdList = progressList.stream().map(MissionProgress::getMissionId).collect(Collectors.toList());
        // 2.find corresponding tasks
        List<Mission> taskList = missionMapper.selectByIds(missionIdList);
        taskList = taskList.stream()
                .filter(x -> x.getActualStartTime().after(DateUtils.truncate(endDate, Calendar.MONTH)))
                .filter(x -> x.getActualStartTime().before(DateUtils.addMonths(DateUtils.truncate(endDate, Calendar.MONTH), 1)))
                .collect(Collectors.toList());
        return taskList;
    }

    @Override
    public List<Task> queryTasksByMission(Long userId, Long missionId) {
        Mission mission = missionMapper.selectByPrimaryKey(missionId);
        return taskMapper.selectByMissionId(mission.getId());
    }

    @Override
    public GroupMissionDTO queryMissionsByGroupId(Long groupId, Long userId) {
        List<Mission> missionList = missionMapper.queryByGroupIdMissions(groupId);
        return buildGroupMissionDTO(groupId, userId, missionList);
    }

}
