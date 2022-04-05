package com.tencent.wxcloudrun.model.DTO;

import com.tencent.wxcloudrun.model.DO.Progress;
import com.tencent.wxcloudrun.model.DO.Task;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class TaskProgressDTO {

    private Task task;

    private Progress progress;

    @Tolerate
    public TaskProgressDTO() {}

}
