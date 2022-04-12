package com.matrictime.network.dao.model;

import lombok.Data;

/**
 * 
 * @author   hexu
 * @date   2022-04-12
 */
@Data
public class UserGroup {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 组id
     */
    private String groupId;

    /**
     * 0 删除  1 正常
     */
    private Boolean isExist;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public Boolean getIsExist() {
        return isExist;
    }

    public void setIsExist(Boolean isExist) {
        this.isExist = isExist;
    }
}