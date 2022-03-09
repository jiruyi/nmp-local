package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 
 * @author   hexu
 * @date   2022-03-03
 */
@Data
public class NmplSignal {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * 信令名称
     */
    private String signalName;

    /**
     * 发送方ip
     */
    private String sendIp;

    /**
     * 接收方ip
     */
    private String receiveIp;

    /**
     * 信令内容
     */
    private String signalContent;

    /**
     * 业务模块
     */
    private String businessModule;

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

    /**
     * 状态true:存在(1)  false:删除(0)
     */
    private Boolean isExist;

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

    public String getSignalName() {
        return signalName;
    }

    public void setSignalName(String signalName) {
        this.signalName = signalName == null ? null : signalName.trim();
    }

    public String getSendIp() {
        return sendIp;
    }

    public void setSendIp(String sendIp) {
        this.sendIp = sendIp == null ? null : sendIp.trim();
    }

    public String getReceiveIp() {
        return receiveIp;
    }

    public void setReceiveIp(String receiveIp) {
        this.receiveIp = receiveIp == null ? null : receiveIp.trim();
    }

    public String getSignalContent() {
        return signalContent;
    }

    public void setSignalContent(String signalContent) {
        this.signalContent = signalContent == null ? null : signalContent.trim();
    }

    public String getBusinessModule() {
        return businessModule;
    }

    public void setBusinessModule(String businessModule) {
        this.businessModule = businessModule == null ? null : businessModule.trim();
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

    public Boolean getIsExist() {
        return isExist;
    }

    public void setIsExist(Boolean isExist) {
        this.isExist = isExist;
    }
}