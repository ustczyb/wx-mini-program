package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.DO.User;
import com.tencent.wxcloudrun.model.common.OpenidResponse;

public interface LoginService {

    /**
     * fetch openid
     * @param jsCode
     * @return
     */
    User login(String jsCode);
}
