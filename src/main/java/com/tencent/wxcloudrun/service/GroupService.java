package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.DO.Group;
import com.tencent.wxcloudrun.model.DTO.UserAccessDTO;

import java.util.List;

public interface GroupService {

    int createGroup(Group groupInfo, Long userId);

    int modifyGroup(Group groupInfo);

    List<Group> queryGroupsOfUser(Long userId);

    int addUserToGroup(Long groupId, Long userId, String role);

    Group queryById(Long groupId);

    List<UserAccessDTO> queryUsersOfGroup(Long groupId);
}
