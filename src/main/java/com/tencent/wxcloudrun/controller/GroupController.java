package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.DO.Group;
import com.tencent.wxcloudrun.model.DO.User;
import com.tencent.wxcloudrun.model.common.ApiResponse;
import com.tencent.wxcloudrun.service.GroupService;
import com.tencent.wxcloudrun.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class GroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @PutMapping("/group/group")
    public ApiResponse createGroup(Group groupInfo, Long userId) {
        Date now = new Date();
        groupInfo.setCtime(now);
        groupInfo.setUtime(now);
        int createRes = groupService.createGroup(groupInfo, userId);
        if (createRes > 0) {
            return ApiResponse.ok();
        } else {
            return ApiResponse.error(-1, "create group failed");
        }
    }

    @PostMapping("/group/group")
    public ApiResponse modifyGroup(Group groupInfo) {
        if (groupInfo.getGroupId() == null) {
            return ApiResponse.error(-2, "input must contains group id");
        }
        int updateRes = groupService.modifyGroup(groupInfo);
        if (updateRes > 0) {
            return ApiResponse.ok();
        } else {
            return ApiResponse.error(-1, "modify group failed");
        }
    }

    @GetMapping("/group/group")
    public ApiResponse getGroupInfo(Long groupId) {
        Group groupInfo = groupService.queryById(groupId);
        if (groupInfo != null) {
            return ApiResponse.ok(groupInfo);
        } else {
            return ApiResponse.error(-1, "query failed");
        }
    }

    @GetMapping("/group/grouplist")
    public ApiResponse searchGroupByUserId(Long userId) {
        List<Group> groupList = groupService.queryGroupsOfUser(userId);
        if (CollectionUtils.isNotEmpty(groupList)) {
            return ApiResponse.ok(groupList);
        } else {
            return ApiResponse.error(-1, "query failed");
        }
    }

    @PutMapping("/group/user")
    public ApiResponse addUserToGroup(Long userId, Long groupId) {
        int addRes = groupService.addUserToGroup(groupId, userId);
        if (addRes > 0) {
            return ApiResponse.ok();
        } else {
            return ApiResponse.error(-1, "group add user error");
        }
    }

    @GetMapping("/group/user")
    public ApiResponse getGroupUserInfo(Long userId) {
        User userInfo = userService.queryByUserId(userId);
        if (userInfo != null) {
            return ApiResponse.ok(userInfo);
        } else {
            LOGGER.warn("query userinfo res is null");
            return ApiResponse.error(-1, "not in database");
        }
    }

    @GetMapping("/group/userlist")
    public ApiResponse getUsersOfGroup(Long groupId) {
        List<User> userList = groupService.queryUsersOfGroup(groupId);
        if (CollectionUtils.isNotEmpty(userList)) {
            return ApiResponse.ok(userList);
        } else {
            return ApiResponse.error(-1, "query error.");
        }
    }

}
