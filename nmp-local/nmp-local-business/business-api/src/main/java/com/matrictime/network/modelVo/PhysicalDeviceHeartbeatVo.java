package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

/**
 * 物理设备心跳表
 * @author   hexu
 * @date   2023-04-20
 */
@Data
public class PhysicalDeviceHeartbeatVo {
    /**
     * 主键(标记两台设备的关联关系，小的ip在前)
     */
    private String ip1Ip2;

    /**
     * 1：不通 2：通
     */
    private String status;

    /**
     * 上报时间
     */
    private Date uploadTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public String getIp1Ip2() {
        return ip1Ip2;
    }

    public void setIp1Ip2(String ip1Ip2) {
        this.ip1Ip2 = ip1Ip2 == null ? null : ip1Ip2.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
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