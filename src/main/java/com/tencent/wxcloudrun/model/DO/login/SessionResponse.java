package com.tencent.wxcloudrun.model.DO.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionResponse {

    private String sessionId;

    private String userId;

    private boolean hasRegistered;
}
