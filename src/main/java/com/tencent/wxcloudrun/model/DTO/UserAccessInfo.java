package com.tencent.wxcloudrun.model.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAccessInfo {

    private String name;

    private String headImage;

    private String sign;

    private String role;
}
