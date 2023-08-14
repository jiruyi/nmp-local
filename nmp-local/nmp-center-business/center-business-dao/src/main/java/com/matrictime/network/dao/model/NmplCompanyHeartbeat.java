package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 小区业务心跳
 * @author   wq
 * @date   2023-08-14
 */
@Data
public class NmplCompanyHeartbeat {
    /**
     * 来源Id
     */
    private String sourceId;

    /**
     * 目标Id
     */
    private String targetId;

    /**
     * 小区唯一编号Id
     */
    private String companyNetworkId;

    /**
     * 连接状态 01:通  02:不通
     */
    private String status;

    /**
     * 上行流量
     */
    private String upValue;

    /**
     * 下行流量
     */
    private String downValue;

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

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId == null ? null : targetId.trim();
    }

    public String getCompanyNetworkId() {
        return companyNetworkId;
    }

    public void setCompanyNetworkId(String companyNetworkId) {
        this.companyNetworkId = companyNetworkId == null ? null : companyNetworkId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getUpValue() {
        return upValue;
    }

    public void setUpValue(String upValue) {
        this.upValue = upValue == null ? null : upValue.trim();
    }

    public String getDownValue() {
        return downValue;
    }

    public void setDownValue(String downValue) {
        this.downValue = downValue == null ? null : downValue.trim();
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