package com.tencent.wxcloudrun.model.DO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.util.Date;

@Builder
@ToString
public class Group {
    private Long groupId;

    private String name;

    private String namePinYin;

    private String headImage;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ctime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date utime;

    @Tolerate
    public Group() {}

    public String getNamePinYin() {
        return namePinYin;
    }

    public void setNamePinYin(String namePinYin) {
        this.namePinYin = namePinYin;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage == null ? null : headImage.trim();
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