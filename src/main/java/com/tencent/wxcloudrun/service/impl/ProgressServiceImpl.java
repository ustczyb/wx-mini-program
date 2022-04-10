package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.annotation.TaskStateCheck;
import com.tencent.wxcloudrun.dao.ProgressMapper;
import com.tencent.wxcloudrun.dao.TaskMapper;
import com.tencent.wxcloudrun.enums.ProgressStateEnum;
import com.tencent.wxcloudrun.enums.TaskStateEnum;
import com.tencent.wxcloudrun.model.DO.Progress;
import com.tencent.wxcloudrun.model.DTO.ProgressStatisticDTO;
import com.tencent.wxcloudrun.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProgressServiceImpl implements ProgressService {

    @Autowired
    private ProgressMapper progressMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<Progress> queryByUserAndTask(Long userId, Long taskId) {
        return progressMapper.selectByUserIdAndTaskIdProgressList(userId, taskId);
    }

    @Override
    public List<Progress> queryByTask(Long taskId) {
        return progressMapper.selectByTaskIdProgressList(taskId);
    }

    @Override
    @TaskStateCheck
    public int modifyProgressState(Long userId, Long taskId, Integer targetState) {
        return progressMapper.updateStateByUserIdAndTaskId(userId, taskId, null, targetState);
    }

    @Override
    @TaskStateCheck
    public int modifyAllProgressState(Long taskId, Integer targetState) {
        return progressMapper.updateStateByUserIdAndTaskId(null, taskId, null, targetState);
    }

    @Override
    @TaskStateCheck
    @Transactional(rollbackFor = Exception.class)
    public int modifyToEndState(Long taskId) {
        progressMapper.updateStateByUserIdAndTaskId(null, taskId, ProgressStateEnum.COMPLETED.getCode(), ProgressStateEnum.FINISHED_WITHOUT_CHECKED.getCode());
        progressMapper.updateStateByUserIdAndTaskId(null, taskId, ProgressStateEnum.CHECKED.getCode(), ProgressStateEnum.FINISHED_WITH_CHECKED.getCode());
        progressMapper.expireTask(taskId);
        taskMapper.batchUpdateTaskState(Collections.singletonList(taskId), TaskStateEnum.ENDED.getCode());
        return 1;
    }

    @Override
    public ProgressStatisticDTO getStatisticInfo(Long taskId) {
        List<Progress> progressList = progressMapper.selectByTaskIdProgressList(taskId);;
        Map<Short, Long> countMap = progressList.stream().collect(Collectors.groupingBy(Progress::getState, Collectors.counting()));;
        return ProgressStatisticDTO.builder().taskId(taskId).countMap(countMap).build();
    }


}
