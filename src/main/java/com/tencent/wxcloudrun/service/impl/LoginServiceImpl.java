package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.model.DO.User;
import com.tencent.wxcloudrun.model.common.OpenidRequest;
import com.tencent.wxcloudrun.model.common.OpenidResponse;
import com.tencent.wxcloudrun.rpc.OpenidFetcher;
import com.tencent.wxcloudrun.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

    public static final String DEBUG_OPENID = "osepd4_H-fDw7VAsNxqWdGJ57h0c";
    public static final String DEBUG_JSCODE = "test";

    @Autowired
    private OpenidFetcher openidFetcher;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String jsCode) {
        OpenidResponse response = null;
        try {
            if (StringUtils.equals(jsCode, DEBUG_JSCODE)) {
                response = OpenidResponse.builder().openId(DEBUG_OPENID).build();
            } else {
                response = openidFetcher.fetchOpenidResponse(OpenidRequest.builder()
                        .appid("wx2e3ceb48ffdf7e40")
                        .secret("d01eb239d198275645d4a3ba506a3ebb")
                        .grantType("authorization_code")
                        .jsCode(jsCode)
                        .build());
                LOGGER.info("openid fetch response: {}", response);
            }
        } catch (IOException e) {
            LOGGER.error("fetch openid throw exception:", e);
        }
        User userInfo = null;
        if (response != null && StringUtils.isNotEmpty(response.getOpenId())) {
            String openId = response.getOpenId();
            userInfo = userMapper.selectByOpenId(openId);
            if (userInfo == null) {
                // 数据库中不存在对应的openId, 注册用户信息
                int createRes = createUserByOpenid(openId);
                userInfo = userMapper.selectByOpenId(openId);
            }
        } else {
            LOGGER.error("openid failed to fetch..., response={}", response);
        }
        return userInfo;
    }

    private int createUserByOpenid(String openId) {
        Date now = new Date();
        return userMapper.insertSelective(User.builder()
                .openId(openId)
                .ctime(now)
                .utime(now)
                .build());
    }


}
