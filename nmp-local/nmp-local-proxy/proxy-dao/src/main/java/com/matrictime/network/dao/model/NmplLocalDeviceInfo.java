package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 本机密钥分发和U盘
 * @author   xxxx
 * @date   2022-08-19
 */
@Data
public class NmplLocalDeviceInfo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型 11:密钥中心 12:生成机 13:缓存机
     */
    private String deviceType;

    /**
     * 备用类型 
     */
    private String otherType;

    /**
     * 接入网时间
     */
    private Date enterNetworkTime;

    /**
     * 设备管理员
     */
    private String deviceAdmain;

    /**
     * 设备备注
     */
    private String remark;

    /**
     * 公网Ip
     */
    private String publicNetworkIp;

    /**
     * 公网端口
     */
    private String publicNetworkPort;

    /**
     * 内网Ip
     */
    private String lanIp;

    /**
     * 内网端口
     */
    private String lanPort;

    /**
     * 设备状态 01:静态  02:激活  03:禁用 04:下线
     */
    private String stationStatus;

    /**
     * 设备入网码
     */
    private String stationNetworkId;

    /**
     * 加密随机数
     */
    private String stationRandomSeed;

    /**
     * 关联区域
     */
    private String relationOperatorId;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 1:存在 0:删除
     */
    private Boolean isExist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getOtherType() {
        return otherType;
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType == null ? null : otherType.trim();
    }

    public Date getEnterNetworkTime() {
        return enterNetworkTime;
    }

    public void setEnterNetworkTime(Date enterNetworkTime) {
        this.enterNetworkTime = enterNetworkTime;
    }

    public String getDeviceAdmain() {
        return deviceAdmain;
    }

    public void setDeviceAdmain(String deviceAdmain) {
        this.deviceAdmain = deviceAdmain == null ? null : deviceAdmain.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getPublicNetworkIp() {
        return publicNetworkIp;
    }

    public void setPublicNetworkIp(String publicNetworkIp) {
        this.publicNetworkIp = publicNetworkIp == null ? null : publicNetworkIp.trim();
    }

    public String getPublicNetworkPort() {
        return publicNetworkPort;
    }

    public void setPublicNetworkPort(String publicNetworkPort) {
        this.publicNetworkPort = publicNetworkPort == null ? null : publicNetworkPort.trim();
    }

    public String getLanIp() {
        return lanIp;
    }

    public void setLanIp(String lanIp) {
        this.lanIp = lanIp == null ? null : lanIp.trim();
    }

    public String getLanPort() {
        return lanPort;
    }

    public void setLanPort(String lanPort) {
        this.lanPort = lanPort == null ? null : lanPort.trim();
    }

    public String getStationStatus() {
        return stationStatus;
    }

    public void setStationStatus(String stationStatus) {
        this.stationStatus = stationStatus == null ? null : stationStatus.trim();
    }

    public String getStationNetworkId() {
        return stationNetworkId;
    }

    public void setStationNetworkId(String stationNetworkId) {
        this.stationNetworkId = stationNetworkId == null ? null : stationNetworkId.trim();
    }

    public String getStationRandomSeed() {
        return stationRandomSeed;
    }

    public void setStationRandomSeed(String stationRandomSeed) {
        this.stationRandomSeed = stationRandomSeed == null ? null : stationRandomSeed.trim();
    }

    public String getRelationOperatorId() {
        return relationOperatorId;
    }

    public void setRelationOperatorId(String relationOperatorId) {
        this.relationOperatorId = relationOperatorId == null ? null : relationOperatorId.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsExist() {
        return isExist;
    }

    public void setIsExist(Boolean isExist) {
        this.isExist = isExist;
    }
}