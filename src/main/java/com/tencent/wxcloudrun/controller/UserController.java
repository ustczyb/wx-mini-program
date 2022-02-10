package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.DO.User;
import com.tencent.wxcloudrun.model.common.ApiResponse;
import com.tencent.wxcloudrun.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PutMapping("user/info")
    public ApiResponse modifyUserInfo(User userInfo) {
        int updateRes = userService.modifyUserInfo(userInfo);
        if (updateRes > 0) {
            return ApiResponse.ok();
        } else {
            LOGGER.warn("modify userInfo failed, input userInfo:{}", userInfo);
            return ApiResponse.error(-1, "modify userInfo error");
        }
    }
}
