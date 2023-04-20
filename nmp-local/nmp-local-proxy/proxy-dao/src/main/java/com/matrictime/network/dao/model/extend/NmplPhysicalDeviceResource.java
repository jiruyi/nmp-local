package com.matrictime.network.dao.model.extend;

import lombok.Data;

import java.util.Date;

/**
 * 物理设备资源情况信息表
 * @author   hexu
 * @date   2023-04-20
 */
@Data
public class NmplPhysicalDeviceResource {
    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 物理设备ip
     */
    private String deviceIp;

    /**
     * 资源类型 1: cpu 2 内存 3 磁盘 4流量 5 其他
     */
    private String resourceType;

    /**
     * 资源value
     */
    private String resourceValue;

    /**
     * 资源单位
     */
    private String resourceUnit;

    /**
     * 资源占比
     */
    private String resourcePercent;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp == null ? null : deviceIp.trim();
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType == null ? null : resourceType.trim();
    }

    public String getResourceValue() {
        return resourceValue;
    }

    public void setResourceValue(String resourceValue) {
        this.resourceValue = resourceValue == null ? null : resourceValue.trim();
    }

    public String getResourceUnit() {
        return resourceUnit;
    }

    public void setResourceUnit(String resourceUnit) {
        this.resourceUnit = resourceUnit == null ? null : resourceUnit.trim();
    }

    public String getResourcePercent() {
        return resourcePercent;
    }

    public void setResourcePercent(String resourcePercent) {
        this.resourcePercent = resourcePercent == null ? null : resourcePercent.trim();
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