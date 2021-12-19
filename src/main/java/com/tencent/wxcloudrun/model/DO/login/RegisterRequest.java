package com.tencent.wxcloudrun.model.DO.login;

import lombok.Data;

@Data
public class RegisterRequest {

    private String sessionId;

    private String userId;

    private String phone;

    private String verificationCode;

    private String name;

    private String idCode;

}
