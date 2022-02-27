package com.tencent.wxcloudrun.model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserAccessInfo {

    private String name;

    private String headImage;

    private String sign;

    private String role;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date userModifyTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date accessModifyTime;
}
