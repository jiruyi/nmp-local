package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 基站心跳上报信息表
 * @author   xxxx
 * @date   2022-08-23
 */
@Data
public class NmplStationHeartInfo {
    /**
     * 设备id
     */
    private String stationId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 设备入网码
     */
    private String stationNetworkId;

    /**
     * 创建时间
     */
    private Date createTime;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getStationNetworkId() {
        return stationNetworkId;
    }

    public void setStationNetworkId(String stationNetworkId) {
        this.stationNetworkId = stationNetworkId == null ? null : stationNetworkId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}