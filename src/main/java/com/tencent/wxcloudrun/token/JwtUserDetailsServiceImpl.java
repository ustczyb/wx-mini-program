package com.tencent.wxcloudrun.token;

import com.tencent.wxcloudrun.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author james mu
 * @date 2020/9/7 18:16
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String openId) throws UsernameNotFoundException {
        com.tencent.wxcloudrun.model.DO.User user = userMapper.selectByOpenId(openId);
        if (user != null) {
            return new User(user.getOpenId(), user.getUserId().toString(), Collections.emptyList());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + openId);
        }
    }
}