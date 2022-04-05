package com.tencent.wxcloudrun.schedule;

import com.tencent.wxcloudrun.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskStateScheduleJob {

    @Autowired
    private TaskService taskService;

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void moveTaskTsate() {

    }
}
