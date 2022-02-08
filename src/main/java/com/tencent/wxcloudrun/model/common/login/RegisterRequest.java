package com.tencent.wxcloudrun.model.common.login;

import lombok.Data;

@Data
public class RegisterRequest {

    private String sessionId;

    private String phone;

    private String verificationCode;

    private String name;

    private String idCode;

}
