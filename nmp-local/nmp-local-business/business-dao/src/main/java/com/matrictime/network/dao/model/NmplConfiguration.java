package com.matrictime.network.dao.model;

import lombok.Data;

/**
 * 
 * @author   hexu
 * @date   2022-06-21
 */
@Data
public class NmplConfiguration {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * 内网Ip
     */
    private String realIp;

    /**
     * 内网端口
     */
    private String realPort;

    /**
     * 路径
     */
    private String url;

    /**
     * 路径类型 1：配置同步 2：信令启停 3：推送版本文件 4：启动版本文件
     */
    private String type;

    /**
     * 设备类型 1:基站 2 分发机 3 生成机 4 缓存机
     */
    private String deviceType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getRealIp() {
        return realIp;
    }

    public void setRealIp(String realIp) {
        this.realIp = realIp == null ? null : realIp.trim();
    }

    public String getRealPort() {
        return realPort;
    }

    public void setRealPort(String realPort) {
        this.realPort = realPort == null ? null : realPort.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }
}