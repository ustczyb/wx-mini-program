package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.common.OpenidResponse;

public interface LoginService {

    /**
     * fetch openid
     * @param jsCode
     * @return
     */
    OpenidResponse fetchOpenid(String jsCode);
}
