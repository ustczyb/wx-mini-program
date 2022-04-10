package com.tencent.wxcloudrun.model.DTO.login;

import com.tencent.wxcloudrun.model.DO.User;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class LoginResponse {

    private User user;

    private String token;

    @Tolerate
    public LoginResponse() {}
}
