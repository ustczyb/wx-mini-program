package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.DO.MissionProgress;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Mapper
public interface MissionProgressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MissionProgress record);

    int insertSelective(MissionProgress record);

    MissionProgress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MissionProgress record);

    int updateByPrimaryKey(MissionProgress record);

    int updateLastViewTime(Long userId, Long missionId);

    List<MissionProgress> selectByUserIdProgressList(Long userId);

    List<MissionProgress> selectByUserIdAndTaskIds(Map<String, Object> buildParamMap);

    List<MissionProgress> selectByMissionIdProgressList(Long missionId);

    MissionProgress selectByUserIdAndMissionIdProgressList(Long userId, Long missionId);
}