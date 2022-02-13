package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.DO.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByUserId(Long id);

    User selectByOpenId(String openId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}