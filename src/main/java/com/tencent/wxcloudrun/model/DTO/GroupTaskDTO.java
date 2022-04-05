package com.tencent.wxcloudrun.model.DTO;

import com.tencent.wxcloudrun.model.DO.Group;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

@Data
@Builder
public class GroupTaskDTO {

    private Group group;

    private List<TaskProgressDTO> taskList;

    @Tolerate
    public GroupTaskDTO() {}
}
