package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.DO.MessageBoard;

public interface MessageBoardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MessageBoard record);

    int insertSelective(MessageBoard record);

    MessageBoard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessageBoard record);

    int updateByPrimaryKey(MessageBoard record);
}