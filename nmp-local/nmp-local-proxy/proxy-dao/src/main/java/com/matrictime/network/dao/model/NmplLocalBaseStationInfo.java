package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 本机基站信息表
 * @author   xxxx
 * @date   2022-08-19
 */
@Data
public class NmplLocalBaseStationInfo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备id
     */
    private String stationId;

    /**
     * 设备名称
     */
    private String stationName;

    /**
     * 设备类型 01:小区内基站 02:小区边界基站
     */
    private String stationType;

    /**
     * 接入网时间
     */
    private Date enterNetworkTime;

    /**
     * 设备管理员
     */
    private String stationAdmain;

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
     * 局域网ip
     */
    private String lanIp;

    /**
     * 局域网port
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
     * 关联小区
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

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType == null ? null : stationType.trim();
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
        this.stationAdmain = stationAdmain == null ? null : stationAdmain.trim();
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