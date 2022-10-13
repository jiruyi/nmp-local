package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 基站下一体机信息上报表
 * @author   hx
 * @date   2022-10-09
 */
@Data
public class NmplPcData {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 基站设备id
     */
    private String stationId;

    /**
     * 一体机设备id
     */
    private String deviceId;

    /**
     * 一体机设备入网码
     */
    private String pcNetworkId;

    /**
     * 设备状态 1:接入  2:未接入
     */
    private Byte status;

    /**
     * 消耗密钥量(单位byte)
     */
    private Integer keyNum;

    /**
     * 上报时间
     */
    private Date reportTime;

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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getPcNetworkId() {
        return pcNetworkId;
    }

    public void setPcNetworkId(String pcNetworkId) {
        this.pcNetworkId = pcNetworkId == null ? null : pcNetworkId.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(Integer keyNum) {
        this.keyNum = keyNum;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }
}