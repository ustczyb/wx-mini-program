package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.GroupAccessMapper;
import com.tencent.wxcloudrun.dao.GroupMapper;
import com.tencent.wxcloudrun.model.DO.Group;
import com.tencent.wxcloudrun.model.DO.GroupAccess;
import com.tencent.wxcloudrun.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    private GroupMapper groupMapper;

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
        Long groupId = groupInfo.getId();
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
    public List<Group> queryGroupsOfUser(Long userId) {
        List<GroupAccess> groupAccessList = groupAccessMapper.selectByUserId(userId);
        LOGGER.info("group nums: {}", groupAccessList.size());
        List<Long> groupIdList = groupAccessList.stream().map(GroupAccess::getGroupId).collect(Collectors.toList());
        LOGGER.info("group ids: {}", groupIdList);
        return groupMapper.selectByPrimaryKeys(groupIdList);
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
}
