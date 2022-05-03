package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.DO.Mission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Mission record);

    int insertSelective(Mission record);

    Mission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Mission record);

    int updateByPrimaryKeyWithBLOBs(Mission record);

    int updateByPrimaryKey(Mission record);

    List<Mission> selectByIds(List<Long> missionIdList);

    List<Mission> selectByCreateUserIdTasks(Long userId);

    List<Mission> queryByGroupIdMissions(Long groupId);
}