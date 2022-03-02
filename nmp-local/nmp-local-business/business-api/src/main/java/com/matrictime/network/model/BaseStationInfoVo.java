package com.matrictime.network.model;

import java.util.Date;

public class BaseStationInfoVo {
    private String id;

    private String stationId;

    private String stationName;

    private String stationType;

    private Date enterNetworkTime;

    private String stationAdmain;

    private String remark;

    private String publicNetworkIp;

    private String publicNetworkPort;

    private String lanIp;

    private String lanPort;

    private String stationStatus;

    private String stationNetworkId;

    private String stationRandomSeed;

    private String relationOperatorId;

    private String createUser;

    private String createTime;

    private String updateUser;

    private String updateTime;

    private Boolean isExist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    public Date getEnterNetworkTime() {
        return enterNetworkTime;
    }

    public void setEnterNetworkTime(Date enterNetworkTime) {
        this.enterNetworkTime = enterNetworkTime;
    }

    public String getStationAdmain() {
        return stationAdmain;
    }

    public void setStationAdmain(String stationAdmain) {
        this.stationAdmain = stationAdmain;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPublicNetworkIp() {
        return publicNetworkIp;
    }

    public void setPublicNetworkIp(String publicNetworkIp) {
        this.publicNetworkIp = publicNetworkIp;
    }

    public String getPublicNetworkPort() {
        return publicNetworkPort;
    }

    public void setPublicNetworkPort(String publicNetworkPort) {
        this.publicNetworkPort = publicNetworkPort;
    }

    public String getLanIp() {
        return lanIp;
    }

    public void setLanIp(String lanIp) {
        this.lanIp = lanIp;
    }

    public String getLanPort() {
        return lanPort;
    }

    public void setLanPort(String lanPort) {
        this.lanPort = lanPort;
    }

    public String getStationStatus() {
        return stationStatus;
    }

    public void setStationStatus(String stationStatus) {
        this.stationStatus = stationStatus;
    }

    public String getStationNetworkId() {
        return stationNetworkId;
    }

    public void setStationNetworkId(String stationNetworkId) {
        this.stationNetworkId = stationNetworkId;
    }

    public String getStationRandomSeed() {
        return stationRandomSeed;
    }

    public void setStationRandomSeed(String stationRandomSeed) {
        this.stationRandomSeed = stationRandomSeed;
    }

    public String getRelationOperatorId() {
        return relationOperatorId;
    }

    public void setRelationOperatorId(String relationOperatorId) {
        this.relationOperatorId = relationOperatorId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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

    public Boolean getExist() {
        return isExist;
    }

    public void setExist(Boolean exist) {
        isExist = exist;
    }
}