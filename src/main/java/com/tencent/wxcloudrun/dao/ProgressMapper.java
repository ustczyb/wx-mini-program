package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.DO.Progress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProgressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Progress record);

    int insertSelective(Progress record);

    Progress selectByPrimaryKey(Integer id);

    List<Progress> selectByUserIdProgressList(Long userId);

    List<Progress> selectByUserIdAndTaskIdProgressList(Long userId, Long taskId);

    int updateByPrimaryKeySelective(Progress record);

    int updateStateByUserIdAndTaskId(Long userId, Long taskId, int targetState);

    int updateByPrimaryKey(Progress record);
}