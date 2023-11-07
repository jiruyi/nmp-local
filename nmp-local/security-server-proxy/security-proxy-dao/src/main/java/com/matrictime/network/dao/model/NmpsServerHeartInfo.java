package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 安全服务器心跳上报信息表
 * @author   cxk
 * @date   2023-11-06
 */
@Data
public class NmpsServerHeartInfo {
    /**
     * 入网id
     */
    private String networkId;

    /**
     * 状态 0：内外网正常 1：外网异常
     */
    private Short serverStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId == null ? null : networkId.trim();
    }

    public Short getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(Short serverStatus) {
        this.serverStatus = serverStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}