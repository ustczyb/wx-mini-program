package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.DO.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface TaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Long id);

    List<Task> selectByIds(List<Long> ids);

    List<Task> selectByCreateUserIdTasks(Long createUserId);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKeyWithBLOBs(Task record);

    int updateByPrimaryKey(Task record);

    int batchUpdateTaskState(@Param("taskIds") List<Long> taskIds, @Param("state") int state);

    List<Task> queryByGroupIdTasks(Long groupId);

    List<Task> queryByEnddate(@Param("startTime")Date startTime, @Param("endTime") Date endTime);

    List<Task> selectByMissionId(Long missionId);
}