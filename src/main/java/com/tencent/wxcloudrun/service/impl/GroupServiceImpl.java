package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.GroupAccessMapper;
import com.tencent.wxcloudrun.dao.GroupMapper;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.model.DO.Group;
import com.tencent.wxcloudrun.model.DO.GroupAccess;
import com.tencent.wxcloudrun.model.DO.User;
import com.tencent.wxcloudrun.service.GroupService;
import com.tencent.wxcloudrun.utils.PinYinUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupAccessMapper groupAccessMapper;

    @Override
    public int createGroup(Group groupInfo, Long userId) {
        // TODO transection
        int insertRes = groupMapper.insertSelective(groupInfo);
        if (insertRes <= 0) {
            LOGGER.error("create group error. groupInfo:{}, userId:{}", groupInfo, userId);
            return 0;
        }
        Long groupId = groupInfo.getGroupId();
        Date now = new Date();
        return groupAccessMapper.insertSelective(GroupAccess.builder()
                        .groupId(groupId)
                        .userId(userId)
                        .valid((byte) 1)
                        .ctime(now)
                        .utime(now)
                        .role(1)        // TODO role
                .build());
    }

    @Override
    public int modifyGroup(Group groupInfo) {
        int updateRes = groupMapper.updateByPrimaryKeySelective(groupInfo);
        if (updateRes <= 0) {
            LOGGER.error("modify group failed. groupInfo={}", groupInfo);
            return 0;
        }
        return updateRes;
    }

    @Override
    public List<Group> queryGroupsOfUser(Long userId) {
        List<GroupAccess> groupAccessList = groupAccessMapper.selectByUserId(userId);
        LOGGER.info("group nums: {}", groupAccessList.size());
        List<Long> groupIdList = groupAccessList.stream().map(GroupAccess::getGroupId).collect(Collectors.toList());
        LOGGER.info("group ids: {}", groupIdList);
        List<Group> groupList = groupMapper.selectByPrimaryKeys(groupIdList);
        groupList.forEach(group -> group.setNamePinYin(PinYinUtils.getPinyin(group.getName(), StringUtils.EMPTY)));
        groupList.sort(Comparator.comparing(Group::getNamePinYin));
        return groupList;
    }

    @Override
    public int addUserToGroup(Long groupId, Long userId) {
        Date now = new Date();
        return groupAccessMapper.insertSelective(GroupAccess.builder()
                .groupId(groupId)
                .userId(userId)
                .valid((byte) 1)
                .ctime(now)
                .utime(now)
                .role(1)
                .build());
    }

    @Override
    public Group queryById(Long groupId) {
        return groupMapper.selectByPrimaryKey(groupId);
    }

    @Override
    public List<User> queryUsersOfGroup(Long groupId) {
        List<GroupAccess> groupAccessList = groupAccessMapper.selectByGroupId(groupId);
        LOGGER.info("user nums: {}", groupAccessList.size());
        List<Long> userIdList = groupAccessList.stream().map(GroupAccess::getUserId).collect(Collectors.toList());
        LOGGER.info("user ids: {}", userIdList);
        List<User> userList = userMapper.selectByUserIds(userIdList);
        return userList;
    }
}
