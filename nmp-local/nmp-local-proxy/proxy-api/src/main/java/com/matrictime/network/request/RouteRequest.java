package com.matrictime.network.request;


public class RouteRequest extends BaseRequest{
    private String id;

    private String accessDeviceId;

    private String boundaryDeviceId;

    private int conditionType;

    private String stationNetworkId;

    private String createUser;

    private String createTime;

    private String updateTime;

    private String updateUser;

    private Byte isExist;

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

    public int getConditionType() {
        return conditionType;
    }

    public void setConditionType(int conditionType) {
        this.conditionType = conditionType;
    }

    public String getStationNetworkId() {
        return stationNetworkId;
    }

    public void setStationNetworkId(String stationNetworkId) {
        this.stationNetworkId = stationNetworkId;
    }
}