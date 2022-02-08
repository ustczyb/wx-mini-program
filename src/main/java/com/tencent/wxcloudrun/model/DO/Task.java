package com.tencent.wxcloudrun.model.DO;

import java.util.Date;

public class Task {
    private Long id;

    private Long groupId;

    private Long createUserId;

    private Byte valid;

    private Integer state;

    private String title;

    private Byte triggerType;

    private Byte taskType;

    private Date exceptStartTime;

    private Date actualStartTime;

    private Date exceptFinishedTime;

    private Date actualFinishedTime;

    private Long bbsId;

    private Date ctime;

    private Date utime;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Byte getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Byte triggerType) {
        this.triggerType = triggerType;
    }

    public Byte getTaskType() {
        return taskType;
    }

    public void setTaskType(Byte taskType) {
        this.taskType = taskType;
    }

    public Date getExceptStartTime() {
        return exceptStartTime;
    }

    public void setExceptStartTime(Date exceptStartTime) {
        this.exceptStartTime = exceptStartTime;
    }

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getExceptFinishedTime() {
        return exceptFinishedTime;
    }

    public void setExceptFinishedTime(Date exceptFinishedTime) {
        this.exceptFinishedTime = exceptFinishedTime;
    }

    public Date getActualFinishedTime() {
        return actualFinishedTime;
    }

    public void setActualFinishedTime(Date actualFinishedTime) {
        this.actualFinishedTime = actualFinishedTime;
    }

    public Long getBbsId() {
        return bbsId;
    }

    public void setBbsId(Long bbsId) {
        this.bbsId = bbsId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}