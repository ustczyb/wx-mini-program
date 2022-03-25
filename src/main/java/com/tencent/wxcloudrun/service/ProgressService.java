package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.DO.Progress;

import java.util.List;

public interface ProgressService {

    List<Progress> queryByUserAndTask(Long userId, Long taskId);

    int modifyProgressState(Long userId, Long taskId, int targetState);
}
