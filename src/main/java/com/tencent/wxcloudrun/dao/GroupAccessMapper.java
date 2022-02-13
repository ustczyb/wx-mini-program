package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.DO.GroupAccess;

import java.util.List;

public interface GroupAccessMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupAccess record);

    int insertSelective(GroupAccess record);

    GroupAccess selectByPrimaryKey(Long id);

    List<GroupAccess> selectByUserId(Long userId);

    int updateByPrimaryKeySelective(GroupAccess record);

    int updateByPrimaryKey(GroupAccess record);
}