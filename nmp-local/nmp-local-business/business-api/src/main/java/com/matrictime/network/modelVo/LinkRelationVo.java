package com.matrictime.network.modelVo;

import java.util.Date;

public class LinkRelationVo {
    private String id;

    private String linkName;

    private String linkType;

    private String mainDeviceId;

    private String mainDeviceName;

    private String followDeviceId;

    private String followDeviceName;

    private String mainPublicNetworkIp;

    private String mainPublicNetworkPort;

    private String mainLanIp;

    private String mainLanPort;

    private String followPublicNetworkIp;

    private String followPublicNetworkPort;

    private String followLanIp;

    private String followLanPort;

    private String createUser;

    private Date createTime;

    private Date updateUser;

    private Date updateTime;

    private String isExist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getMainDeviceId() {
        return mainDeviceId;
    }

    public void setMainDeviceId(String mainDeviceId) {
        this.mainDeviceId = mainDeviceId;
    }

    public String getFollowDeviceId() {
        return followDeviceId;
    }

    public void setFollowDeviceId(String followDeviceId) {
        this.followDeviceId = followDeviceId;
    }

    public String getMainPublicNetworkIp() {
        return mainPublicNetworkIp;
    }

    public void setMainPublicNetworkIp(String mainPublicNetworkIp) {
        this.mainPublicNetworkIp = mainPublicNetworkIp;
    }

    public String getMainPublicNetworkPort() {
        return mainPublicNetworkPort;
    }

    public void setMainPublicNetworkPort(String mainPublicNetworkPort) {
        this.mainPublicNetworkPort = mainPublicNetworkPort;
    }

    public String getMainLanIp() {
        return mainLanIp;
    }

    public void setMainLanIp(String mainLanIp) {
        this.mainLanIp = mainLanIp;
    }

    public String getMainLanPort() {
        return mainLanPort;
    }

    public void setMainLanPort(String mainLanPort) {
        this.mainLanPort = mainLanPort;
    }

    public String getFollowPublicNetworkIp() {
        return followPublicNetworkIp;
    }

    public void setFollowPublicNetworkIp(String followPublicNetworkIp) {
        this.followPublicNetworkIp = followPublicNetworkIp;
    }

    public String getFollowPublicNetworkPort() {
        return followPublicNetworkPort;
    }

    public void setFollowPublicNetworkPort(String followPublicNetworkPort) {
        this.followPublicNetworkPort = followPublicNetworkPort;
    }

    public String getFollowLanIp() {
        return followLanIp;
    }

    public void setFollowLanIp(String followLanIp) {
        this.followLanIp = followLanIp;
    }

    public String getFollowLanPort() {
        return followLanPort;
    }

    public void setFollowLanPort(String followLanPort) {
        this.followLanPort = followLanPort;
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

    public Date getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Date updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsExist() {
        return isExist;
    }

    public void setIsExist(String isExist) {
        this.isExist = isExist;
    }

    public String getMainDeviceName() {
        return mainDeviceName;
    }

    public void setMainDeviceName(String mainDeviceName) {
        this.mainDeviceName = mainDeviceName;
    }

    public String getFollowDeviceName() {
        return followDeviceName;
    }

    public void setFollowDeviceName(String followDeviceName) {
        this.followDeviceName = followDeviceName;
    }
}