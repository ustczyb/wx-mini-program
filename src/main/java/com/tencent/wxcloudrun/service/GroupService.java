package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.DO.Group;

import java.util.List;

public interface GroupService {

    int createGroup(Group groupInfo, Long userId);

    List<Group> queryGroupsOfUser(Long userId);

    int addUserToGroup(Long groupId, Long userId);
}
