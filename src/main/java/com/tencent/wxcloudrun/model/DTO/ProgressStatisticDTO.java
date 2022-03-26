package com.tencent.wxcloudrun.model.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Map;

@Data
@Builder
public class ProgressStatisticDTO {

    private Long taskId;

    private Map<Short, Long> countMap;

    @Tolerate
    public ProgressStatisticDTO() {}

}
