package com.tencent.wxcloudrun.model.DO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.experimental.Tolerate;

import java.util.Date;

@Builder
public class Progress {
    private Integer id;

    private Long taskId;

    private Long userId;

    private Long groupId;

    private Short userAccess;

    private Short state;

    private String extension;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ctime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date utime;

    @Tolerate
    public Progress() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
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

    public Short getUserAccess() {
        return userAccess;
    }

    public void setUserAccess(Short userAccess) {
        this.userAccess = userAccess;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}