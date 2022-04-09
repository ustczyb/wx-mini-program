package com.tencent.wxcloudrun.schedule;

import com.tencent.wxcloudrun.dao.TaskMapper;
import com.tencent.wxcloudrun.enums.TaskStateEnum;
import com.tencent.wxcloudrun.model.DO.Task;
import com.tencent.wxcloudrun.service.TaskService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskStateScheduleJob {

    @Autowired
    private TaskMapper taskMapper;

    @Scheduled(fixedRate = 5 * 60 * 1000)
    @Transactional(rollbackFor = Exception.class)
    public void moveTaskState() {
        Date now = new Date();
        List<Task> taskList = taskMapper.queryByEnddate(DateUtils.addMinutes(now, -5), now);
        if (CollectionUtils.isNotEmpty(taskList)) {
            List<Long> taskIds = taskList.stream().map(Task::getTaskId).collect(Collectors.toList());
            taskMapper.batchUpdateTaskState(taskIds, TaskStateEnum.ENDED.getCode());
        }
    }
}
