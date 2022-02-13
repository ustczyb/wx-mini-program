package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.DO.Group;
import com.tencent.wxcloudrun.model.common.ApiResponse;
import com.tencent.wxcloudrun.service.GroupService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

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

    @GetMapping("/group/allgroupbyuser")
    public ApiResponse searchGroupByUserId(Long userId) {
        List<Group> groupList = groupService.queryGroupsOfUser(userId);
        if (CollectionUtils.isNotEmpty(groupList)) {
            return ApiResponse.ok(groupList);
        } else {
            return ApiResponse.error(-1, "query failed");
        }
    }

}
