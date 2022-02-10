package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.controller.UserController;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.model.DO.User;
import com.tencent.wxcloudrun.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public int modifyUserInfo(User userInfo) {
        if (userInfo.getUserId() == null) {
            LOGGER.warn("input userId is null, input userInfo:{}", userInfo);
            return -1;
        }
        return userMapper.updateByPrimaryKeySelective(userInfo);
    }
}
