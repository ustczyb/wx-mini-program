package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.WxCloudRunApplication;
import com.tencent.wxcloudrun.model.DO.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WxCloudRunApplication.class)
@EnableAutoConfiguration
public class RunTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insertUser() {
        User user = new User();
        user.setName("小黑");
        user.setGender((byte) 1);
        user.setPhone("111111112");
        user.setCtime(new Date());
        userMapper.insert(user);
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setId(1L);
        user.setName("小黑");
        user.setGender((byte) 1);
        user.setPhone("1234567890");
        user.setCtime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }
}
