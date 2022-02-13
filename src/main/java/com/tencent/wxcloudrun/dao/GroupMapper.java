package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.DO.Group;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Group record);

    Long insertSelective(Group record);

    Group selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);

    List<Group> selectByPrimaryKeys(List<Long> groupIdList);
}