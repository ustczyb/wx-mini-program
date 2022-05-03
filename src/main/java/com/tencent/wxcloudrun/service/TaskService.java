package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.DO.Mission;
import com.tencent.wxcloudrun.model.DO.Task;
import com.tencent.wxcloudrun.model.DTO.GroupMissionDTO;
import com.tencent.wxcloudrun.model.DTO.GroupTaskDTO;

import java.util.Date;
import java.util.List;

public interface TaskService {

    int createTask(Task task, List<Long> userIdList);

    int modifyTask(Task task, Integer targetProgressState);

    Task queryByTaskId(Long taskId, Long userId);

    GroupTaskDTO queryByGroupId(Long groupId, Long userId);

    List<GroupTaskDTO> queryEffectiveTaskByUserId(Long userId);

    List<GroupTaskDTO> queryTaskByOwner(Long userId);

    List<Task> queryByMonth(Long userId, Date endDay);

    Mission queryByMissionId(Long missionId, Long userId);

    int modifyMission(Mission mission);

    int createMission(Mission mission, List<Long> userIds);

    List<GroupMissionDTO> queryEffectiveMissionByUserId(Long userId);

    List<GroupMissionDTO> queryMissionByOwner(Long userId);

    List<Mission> queryMissionByMonth(Long userId, Date endMonth);

    List<Task> queryTasksByMission(Long userId, Long missionId);

    GroupMissionDTO queryMissionsByGroupId(Long groupId, Long userId);
}
