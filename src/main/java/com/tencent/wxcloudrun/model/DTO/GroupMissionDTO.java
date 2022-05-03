package com.tencent.wxcloudrun.model.DTO;

import com.tencent.wxcloudrun.model.DO.Group;
import com.tencent.wxcloudrun.model.DO.Mission;
import com.tencent.wxcloudrun.model.DO.MissionProgress;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

@Data
@Builder
public class GroupMissionDTO {

    @Data
    @Builder
    public static class MissionProgressDTO {

        private Mission mission;

        private MissionProgress progress;

        @Tolerate
        public MissionProgressDTO() {}
    }

    private Group group;

    private List<MissionProgressDTO> taskList;

    @Tolerate
    public GroupMissionDTO() {}
}
