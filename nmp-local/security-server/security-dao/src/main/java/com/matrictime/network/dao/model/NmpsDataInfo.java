package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 密钥信息数据上报表
 * @author   xxxx
 * @date   2023-11-01
 */
@Data
public class NmpsDataInfo {
    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 入网id
     */
    private String networkId;

    /**
     * 数据值（单位byte）
     */
    private Long dataValue;

    /**
     * 数据类型（1000：剩余上行密钥量 1001：已使用上行密钥量 2000：剩余下行密钥量 2001：已使用下行密钥量）
     */
    private String dataType;

    /**
     * 上报时间
     */
    private Date uploadTime;

    /**
     * 创建时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId == null ? null : networkId.trim();
    }

    public Long getDataValue() {
        return dataValue;
    }

    public void setDataValue(Long dataValue) {
        this.dataValue = dataValue;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
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
}