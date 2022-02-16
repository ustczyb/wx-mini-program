package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.DO.GroupAccess;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupAccessMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupAccess record);

    int insertSelective(GroupAccess record);

    GroupAccess selectByPrimaryKey(Long id);

    List<GroupAccess> selectByUserId(Long userId);

    List<GroupAccess> selectByGroupId(Long groupId);

    int updateByPrimaryKeySelective(GroupAccess record);

    int updateByPrimaryKey(GroupAccess record);
}