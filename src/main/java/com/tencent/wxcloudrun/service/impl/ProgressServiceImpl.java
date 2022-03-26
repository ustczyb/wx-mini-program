package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.ProgressMapper;
import com.tencent.wxcloudrun.model.DO.Progress;
import com.tencent.wxcloudrun.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProgressServiceImpl implements ProgressService {

    @Autowired
    private ProgressMapper progressMapper;

    @Override
    public List<Progress> queryByUserAndTask(Long userId, Long taskId) {
        return progressMapper.selectByUserIdAndTaskIdProgressList(userId, taskId);
    }

    @Override
    public List<Progress> queryByTask(Long taskId) {
        return progressMapper.selectByTaskIdProgressList(taskId);
    }

    @Override
    public int modifyProgressState(Long userId, Long taskId, int targetState) {
        return progressMapper.updateStateByUserIdAndTaskId(userId, taskId, targetState);
    }

    @Override
    public int modifyAllProgressState(Long taskId, int targetState) {
        return progressMapper.updateStateByUserIdAndTaskId(null, taskId, targetState);
    }

    @Override
    public Map<Short, Long> getStatisticInfo(Long taskId) {
        List<Progress> progressList = progressMapper.selectByTaskIdProgressList(taskId);;
        Map<Short, Long> countMap = progressList.stream().collect(Collectors.groupingBy(Progress::getState, Collectors.counting()));;
        return countMap;
    }


}
