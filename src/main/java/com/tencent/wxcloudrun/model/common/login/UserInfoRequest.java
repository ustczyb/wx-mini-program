package com.tencent.wxcloudrun.model.common.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoRequest {

    private String sessionId;

    private String userId;

    private String gender;

    private String college;

    private String birth;

    private String phone;
}
