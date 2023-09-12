package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 小区业务心跳
 * @author   hexu
 * @date   2023-09-12
 */
@Data
public class NmplCompanyHeartbeat {
    /**
     * 主键
     */
    private Long id;

    /**
     * 来源Id
     */
    private String sourceNetworkId;

    /**
     * 目标Id
     */
    private String targetNetworkId;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceNetworkId() {
        return sourceNetworkId;
    }

    public void setSourceNetworkId(String sourceNetworkId) {
        this.sourceNetworkId = sourceNetworkId == null ? null : sourceNetworkId.trim();
    }

    public String getTargetNetworkId() {
        return targetNetworkId;
    }

    public void setTargetNetworkId(String targetNetworkId) {
        this.targetNetworkId = targetNetworkId == null ? null : targetNetworkId.trim();
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