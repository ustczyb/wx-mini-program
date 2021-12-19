package com.tencent.wxcloudrun.model.DO.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResponse {

    private String userId;

    private String gender;

    private String college;

    private String birth;

    private String phone;
}
