package com.matrictime.network.dao.model;

import lombok.Data;

import java.util.Date;

/**
 * 
 * @author   cxk
 * @date   2023-11-10
 */
@Data
public class NmpsAlarmInfo {
    /**
     * 主键
     */
    private Long alarmId;

    /**
     * 入网id
     */
    private String networkId;

    /**
     * 告警内容
     */
    private String alarmContent;

    /**
     * 级别 1严重 2 紧急 3 一般
     */
    private String alarmLevel;

    /**
     * 操作时间
     */
    private Date alarmUploadTime;

    /**
     * 来源类型 00资源告警 01安全服务器
     */
    private String alarmSourceType;

    /**
     * 告警内容类型  1: cpu 2 内存 3 磁盘 4流量 5 其他
     */
    private String alarmContentType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Long getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Long alarmId) {
        this.alarmId = alarmId;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId == null ? null : networkId.trim();
    }

    public String getAlarmContent() {
        return alarmContent;
    }

    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent == null ? null : alarmContent.trim();
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel == null ? null : alarmLevel.trim();
    }

    public Date getAlarmUploadTime() {
        return alarmUploadTime;
    }

    public void setAlarmUploadTime(Date alarmUploadTime) {
        this.alarmUploadTime = alarmUploadTime;
    }

    public String getAlarmSourceType() {
        return alarmSourceType;
    }

    public void setAlarmSourceType(String alarmSourceType) {
        this.alarmSourceType = alarmSourceType == null ? null : alarmSourceType.trim();
    }

    public String getAlarmContentType() {
        return alarmContentType;
    }

    public void setAlarmContentType(String alarmContentType) {
        this.alarmContentType = alarmContentType == null ? null : alarmContentType.trim();
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