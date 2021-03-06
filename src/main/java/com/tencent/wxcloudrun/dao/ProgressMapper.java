package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.DO.Progress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mapper
public interface ProgressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Progress record);

    int insertSelective(Progress record);

    Progress selectByPrimaryKey(Integer id);

    List<Progress> selectByUserIdProgressList(Long userId);

    List<Progress> selectByTaskIdProgressList(Long taskId);

    List<Progress> selectByUserIdAndTaskIdProgressList(Long userId, Long taskId);

    List<Progress> selectByUserIdAndTaskIds(Map<String, Object> paramMap);

    List<Progress> selectSelective(Progress progress);

    int updateByPrimaryKeySelective(Progress record);

    int updateStateByUserIdAndTaskId(Long userId, Long taskId, Integer curState, Integer targetState);

    int updateLastViewTime(Long userId, Long taskId);

    int expireTask(Long taskId);

    int updateByPrimaryKey(Progress record);
}