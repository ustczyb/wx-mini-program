package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.DO.User;

public interface UserService {

    int modifyUserInfo(User userInfo);

    User queryByUserId(Long userId);
}
