package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.DO.MissionProgress;
import com.tencent.wxcloudrun.model.DO.Progress;
import com.tencent.wxcloudrun.model.DTO.ProgressStatisticDTO;

import java.util.List;

public interface ProgressService {

    List<Progress> queryByUserAndTask(Long userId, Long taskId);

    List<Progress> queryByTask(Long taskId);

    int modifyProgressState(Long userId, Long taskId, Integer targetState);

    int modifyAllProgressState(Long taskId, Integer targetState);

    int modifyToEndState(Long taskId);

    ProgressStatisticDTO getStatisticInfo(Long taskId);

    MissionProgress queryByUserAndMission(Long userId, Long missionId);

    List<MissionProgress> queryByMission(Long missionId);

    ProgressStatisticDTO getMissionStatisticInfo(Long missionId);
}
