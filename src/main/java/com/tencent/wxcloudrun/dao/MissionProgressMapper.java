package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.DO.MissionProgress;

public interface MissionProgressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MissionProgress record);

    int insertSelective(MissionProgress record);

    MissionProgress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MissionProgress record);

    int updateByPrimaryKey(MissionProgress record);
}