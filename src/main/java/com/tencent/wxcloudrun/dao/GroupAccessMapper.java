package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.DO.GroupAccess;
import com.tencent.wxcloudrun.model.DO.GroupAccessExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GroupAccessMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupAccess record);

    int insertSelective(GroupAccess record);

    List<GroupAccess> selectByExample(GroupAccessExample example);

    GroupAccess selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GroupAccess record, @Param("example") GroupAccessExample example);

    int updateByExample(@Param("record") GroupAccess record, @Param("example") GroupAccessExample example);

    int updateByPrimaryKeySelective(GroupAccess record);

    int updateByPrimaryKey(GroupAccess record);
}