package com.matrictime.network.dao.model;

import java.util.Date;

public class NmplRoute {
    private Long id;

    private String accessDeviceId;

    private String boundaryDeviceId;

    private String createUser;

    private Date createTime;

    private Date updateTime;

    private Date updateUser;

    private Byte isExist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessDeviceId() {
        return accessDeviceId;
    }

    public void setAccessDeviceId(String accessDeviceId) {
        this.accessDeviceId = accessDeviceId;
    }

    public String getBoundaryDeviceId() {
        return boundaryDeviceId;
    }

    public void setBoundaryDeviceId(String boundaryDeviceId) {
        this.boundaryDeviceId = boundaryDeviceId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Date updateUser) {
        this.updateUser = updateUser;
    }

    public Byte getIsExist() {
        return isExist;
    }

    public void setIsExist(Byte isExist) {
        this.isExist = isExist;
    }
}