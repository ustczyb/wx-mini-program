package com.tencent.wxcloudrun.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskTypeDTO {

    private String desc;

    private Integer code;

    private List<TaskTypeDTO> subTypeList;
}
