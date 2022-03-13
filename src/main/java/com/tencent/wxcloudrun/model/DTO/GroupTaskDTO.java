package com.tencent.wxcloudrun.model.DTO;

import com.tencent.wxcloudrun.model.DO.Group;
import com.tencent.wxcloudrun.model.DO.Task;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

@Data
@Builder
public class GroupTaskDTO {

    private Group group;

    private List<Task> taskList;

    @Tolerate
    public GroupTaskDTO() {}
}
