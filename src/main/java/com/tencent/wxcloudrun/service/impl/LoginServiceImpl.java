package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.model.common.OpenidRequest;
import com.tencent.wxcloudrun.model.common.OpenidResponse;
import com.tencent.wxcloudrun.rpc.OpenidFetcher;
import com.tencent.wxcloudrun.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private OpenidFetcher openidFetcher;

    @Override
    public OpenidResponse fetchOpenid(String jsCode) {
        OpenidResponse response = null;
        try {
            response = openidFetcher.fetchOpenidResponse(OpenidRequest.builder()
                            .appid("wx2e3ceb48ffdf7e40")
                            .secret("d01eb239d198275645d4a3ba506a3ebb")
                            .grantType("authorization_code")
                            .jsCode(jsCode)
                            .build());
        } catch (IOException e) {
            LOGGER.error("fetch openid throw exception:", e);
        }
        return response;
    }
}
