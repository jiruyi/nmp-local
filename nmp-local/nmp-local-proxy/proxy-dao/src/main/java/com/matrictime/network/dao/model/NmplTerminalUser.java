package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 终端用户表
 * @author   xxxx
 * @date   2023-04-19
 */
@Data
public class NmplTerminalUser {
    /**
     * 终端设备Id
     */
    private String terminalNetworkId;

    /**
     * 所属设备Id
     */
    private String parentDeviceId;

    /**
     * 用户状态 01:密钥匹配  02:注册  03:上线 04:下线 05:注销
     */
    private String terminalStatus;

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

    public String getTerminalNetworkId() {
        return terminalNetworkId;
    }

    public void setTerminalNetworkId(String terminalNetworkId) {
        this.terminalNetworkId = terminalNetworkId == null ? null : terminalNetworkId.trim();
    }

    public String getParentDeviceId() {
        return parentDeviceId;
    }

    public void setParentDeviceId(String parentDeviceId) {
        this.parentDeviceId = parentDeviceId == null ? null : parentDeviceId.trim();
    }

    public String getTerminalStatus() {
        return terminalStatus;
    }

    public void setTerminalStatus(String terminalStatus) {
        this.terminalStatus = terminalStatus == null ? null : terminalStatus.trim();
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