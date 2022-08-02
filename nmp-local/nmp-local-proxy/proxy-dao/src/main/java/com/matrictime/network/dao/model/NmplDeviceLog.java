package com.matrictime.network.dao.model;

import lombok.Data;

import java.util.Date;

/**
 * 
 * @author   hexu
 * @date   2022-03-07
 */
@Data
public class NmplDeviceLog {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String devcieName;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 设备ip
     */
    private String deviceIp;

    /**
     * 设备操作 关机 初始化。。。
     */
    private String operType;

    /**
     * 操作描述
     */
    private String operDesc;

    /**
     * 操作人
     */
    private String operUser;

    /**
     * 操作时间
     */
    private Date operTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

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

    public String getDevcieName() {
        return devcieName;
    }

    public void setDevcieName(String devcieName) {
        this.devcieName = devcieName == null ? null : devcieName.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp == null ? null : deviceIp.trim();
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType == null ? null : operType.trim();
    }

    public String getOperDesc() {
        return operDesc;
    }

    public void setOperDesc(String operDesc) {
        this.operDesc = operDesc == null ? null : operDesc.trim();
    }

    public String getOperUser() {
        return operUser;
    }

    public void setOperUser(String operUser) {
        this.operUser = operUser == null ? null : operUser.trim();
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
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
}