package com.tencent.wxcloudrun.model.DO;

import lombok.Builder;
import lombok.experimental.Tolerate;

import java.util.Date;

@Builder
public class MissionProgress {
    private Integer id;

    private Long missionId;

    private Long userId;

    private Long groupId;

    private Short userAccess;

    private Short state;

    private String ext;

    private Date lastViewTime;

    private Date ctime;

    private Date utime;

    @Tolerate
    public MissionProgress() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Short getUserAccess() {
        return userAccess;
    }

    public void setUserAccess(Short userAccess) {
        this.userAccess = userAccess;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext == null ? null : ext.trim();
    }

    public Date getLastViewTime() {
        return lastViewTime;
    }

    public void setLastViewTime(Date lastViewTime) {
        this.lastViewTime = lastViewTime;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }
}