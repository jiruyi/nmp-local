package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 运行系统资源信息表
 * @author   hexu
 * @date   2023-04-20
 */
@Data
public class NmplSystemResource {
    /**
     * 主键（系统id，关联基站和设备表）
     */
    private String systemId;

    /**
     * 系统类别
     */
    private String systemType;

    /**
     * 启动时间
     */
    private Date startTime;

    /**
     * 运行时长，以秒为单位
     */
    private Long runTime;

    /**
     * CPU占比，百分比形式
     */
    private String cpuPercent;

    /**
     * 内存占比，百分比形式
     */
    private String memoryPercent;

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

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType == null ? null : systemType.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Long getRunTime() {
        return runTime;
    }

    public void setRunTime(Long runTime) {
        this.runTime = runTime;
    }

    public String getCpuPercent() {
        return cpuPercent;
    }

    public void setCpuPercent(String cpuPercent) {
        this.cpuPercent = cpuPercent == null ? null : cpuPercent.trim();
    }

    public String getMemoryPercent() {
        return memoryPercent;
    }

    public void setMemoryPercent(String memoryPercent) {
        this.memoryPercent = memoryPercent == null ? null : memoryPercent.trim();
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