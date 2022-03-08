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

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKeyWithBLOBs(Task record);

    int updateByPrimaryKey(Task record);

    List<Task> queryByGroupIdTasks(Long groupId);

    List<Task> queryByEnddate(@Param("startTime")Date startTime, @Param("endTime") Date endTime);
}