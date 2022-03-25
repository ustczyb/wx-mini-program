package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.ProgressMapper;
import com.tencent.wxcloudrun.model.DO.Progress;
import com.tencent.wxcloudrun.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    @Autowired
    private ProgressMapper progressMapper;

    @Override
    public List<Progress> queryByUserAndTask(Long userId, Long taskId) {
        return progressMapper.selectByUserIdAndTaskIdProgressList(userId, taskId);
    }
}
