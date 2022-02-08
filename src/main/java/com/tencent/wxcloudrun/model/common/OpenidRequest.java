package com.tencent.wxcloudrun.model.common;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class OpenidRequest {

    private String appid;

    private String secret;

    private String jsCode;

    private String grantType;

    @Tolerate
    public OpenidRequest() {}
}
