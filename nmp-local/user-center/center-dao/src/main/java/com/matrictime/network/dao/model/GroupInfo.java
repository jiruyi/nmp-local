package com.matrictime.network.dao.model;

import lombok.Data;

import java.util.Date;

/**
 * 
 * @author   hexu
 * @date   2022-05-09
 */
@Data
public class GroupInfo {
    /**
     * 主键 组id
     */
    private Long groupId;

    /**
     * 组名称
     */
    private String groupName;

    /**
     *  0 删除  1 存在
     */
    private Boolean isExist;

    /**
     * 组所属人
     */
    private String owner;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 0非默认 1默认
     */
    private Boolean defaultGroup;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Boolean getIsExist() {
        return isExist;
    }

    public void setIsExist(Boolean isExist) {
        this.isExist = isExist;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getDefaultGroup() {
        return defaultGroup;
    }

    public void setDefaultGroup(Boolean defaultGroup) {
        this.defaultGroup = defaultGroup;
    }
}