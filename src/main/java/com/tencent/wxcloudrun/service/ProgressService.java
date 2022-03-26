package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.DO.Progress;

import java.util.List;
import java.util.Map;

public interface ProgressService {

    List<Progress> queryByUserAndTask(Long userId, Long taskId);

    List<Progress> queryByTask(Long taskId);

    int modifyProgressState(Long userId, Long taskId, int targetState);

    int modifyAllProgressState(Long taskId, int targetState);

    Map<Short, Long> getStatisticInfo(Long taskId);
}
