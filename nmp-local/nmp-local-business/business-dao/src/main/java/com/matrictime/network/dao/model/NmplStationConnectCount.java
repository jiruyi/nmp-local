package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 当前用户在线表
 * @author   hexu
 * @date   2023-08-08
 */
@Data
public class NmplStationConnectCount {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备Id
     */
    private String stationId;

    /**
     * 当前用户数
     */
    private String currentConnectCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 上传时间
     */
    private Date uploadTime;

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

    public String getCurrentConnectCount() {
        return currentConnectCount;
    }

    public void setCurrentConnectCount(String currentConnectCount) {
        this.currentConnectCount = currentConnectCount == null ? null : currentConnectCount.trim();
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

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}