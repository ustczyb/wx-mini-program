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

    int updateByPrimaryKeySelective(Progress record);

    int updateByPrimaryKey(Progress record);
}