package com.matrictime.network.modelVo;

public class RouteVo {
    private String id;

    private String accessDeviceId;

    private String boundaryDeviceId;

    private String boundaryDevicePublicIp;

    private String boundaryDevicePublicPort;

    private String boundaryDeviceLanIp;

    private String boundaryDeviceLanPort;

    private String stationName;

    private String createUser;

    private String createTime;

    private String updateTime;

    private String updateUser;

    private Byte isExist;

    private String nickName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Byte getIsExist() {
        return isExist;
    }

    public void setIsExist(Byte isExist) {
        this.isExist = isExist;
    }

    public String getBoundaryDevicePublicIp() {
        return boundaryDevicePublicIp;
    }

    public void setBoundaryDevicePublicIp(String boundaryDevicePublicIp) {
        this.boundaryDevicePublicIp = boundaryDevicePublicIp;
    }

    public String getBoundaryDevicePublicPort() {
        return boundaryDevicePublicPort;
    }

    public void setBoundaryDevicePublicPort(String boundaryDevicePublicPort) {
        this.boundaryDevicePublicPort = boundaryDevicePublicPort;
    }

    public String getBoundaryDeviceLanIp() {
        return boundaryDeviceLanIp;
    }

    public void setBoundaryDeviceLanIp(String boundaryDeviceLanIp) {
        this.boundaryDeviceLanIp = boundaryDeviceLanIp;
    }

    public String getBoundaryDeviceLanPort() {
        return boundaryDeviceLanPort;
    }

    public void setBoundaryDeviceLanPort(String boundaryDeviceLanPort) {
        this.boundaryDeviceLanPort = boundaryDeviceLanPort;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}