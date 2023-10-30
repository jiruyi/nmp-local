package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * ip管理
 * @author  wangqiang
 * @date   2023-10-27
 */
@Data
public class NmpsIpManage {
    /**
     * 主键
     */
    private Long id;

    /**
     * 管理类型 01:基站 02:CA 03:DNS
     */
    private String manageType;

    /**
     * 入网id
     */
    private String networkId;

    /**
     * 服务器名称
     */
    private String securitySeverName;

    /**
     * 安全服务器ip
     */
    private String securitySeverIp;

    /**
     * 基站类型 00:基站 30:备用基站
     */
    private String stationType;

    /**
     * 接入方式 01:固定接入 02:动态接入
     */
    private String accessMethod;

    /**
     * 域名
     */
    private String domainName;

    /**
     * 通信ip
     */
    private String publicIp;

    /**
     * 通信port
     */
    private String publicPort;

    /**
     * 隐私ip
     */
    private String lanIp;

    /**
     * 接收端口
     */
    private String receivePort;

    /**
     * 发送端口
     */
    private String sendPort;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 1:存在 0:删除
     */
    private Boolean isExist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManageType() {
        return manageType;
    }

    public void setManageType(String manageType) {
        this.manageType = manageType == null ? null : manageType.trim();
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId == null ? null : networkId.trim();
    }

    public String getSecuritySeverName() {
        return securitySeverName;
    }

    public void setSecuritySeverName(String securitySeverName) {
        this.securitySeverName = securitySeverName == null ? null : securitySeverName.trim();
    }

    public String getSecuritySeverIp() {
        return securitySeverIp;
    }

    public void setSecuritySeverIp(String securitySeverIp) {
        this.securitySeverIp = securitySeverIp == null ? null : securitySeverIp.trim();
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType == null ? null : stationType.trim();
    }

    public String getAccessMethod() {
        return accessMethod;
    }

    public void setAccessMethod(String accessMethod) {
        this.accessMethod = accessMethod == null ? null : accessMethod.trim();
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName == null ? null : domainName.trim();
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp == null ? null : publicIp.trim();
    }

    public String getPublicPort() {
        return publicPort;
    }

    public void setPublicPort(String publicPort) {
        this.publicPort = publicPort == null ? null : publicPort.trim();
    }

    public String getLanIp() {
        return lanIp;
    }

    public void setLanIp(String lanIp) {
        this.lanIp = lanIp == null ? null : lanIp.trim();
    }

    public String getReceivePort() {
        return receivePort;
    }

    public void setReceivePort(String receivePort) {
        this.receivePort = receivePort == null ? null : receivePort.trim();
    }

    public String getSendPort() {
        return sendPort;
    }

    public void setSendPort(String sendPort) {
        this.sendPort = sendPort == null ? null : sendPort.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
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