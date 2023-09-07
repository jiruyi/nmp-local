package com.matrictime.network.dao.model;

import lombok.Data;

import java.util.Date;

/**
 * 链路信息表
 * @author   hexu
 * @date   2023-09-06
 */
@Data
public class NmplLink {
    /**
     * 主键
     */
    private Long id;

    /**
     * 链路名称
     */
    private String linkName;

    /**
     * 链路类型: 1:单向,2:双向
     */
    private Short linkType;

    /**
     * 链路关系: 01:边界基站-边界基站,02:接入基站-密钥中心,03:边界基站-密钥中心,04:采集系统-边界基站
     */
    private String linkRelation;

    /**
     * 主设备id
     */
    private String mainDeviceId;

    /**
     * 从设备id
     */
    private String followDeviceId;

    /**
     * 从设备入网码
     */
    private String followNetworkId;

    /**
     * 从设备ip
     */
    private String followIp;

    /**
     * 从设备端口
     */
    private String followPort;

    /**
     * 主动发起认证 1:开启 0:关闭
     */
    private Boolean activeAuth;

    /**
     * 是否启用 1:启动 0:禁止
     */
    private Boolean isOn;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
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

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName == null ? null : linkName.trim();
    }

    public Short getLinkType() {
        return linkType;
    }

    public void setLinkType(Short linkType) {
        this.linkType = linkType;
    }

    public String getLinkRelation() {
        return linkRelation;
    }

    public void setLinkRelation(String linkRelation) {
        this.linkRelation = linkRelation == null ? null : linkRelation.trim();
    }

    public String getMainDeviceId() {
        return mainDeviceId;
    }

    public void setMainDeviceId(String mainDeviceId) {
        this.mainDeviceId = mainDeviceId == null ? null : mainDeviceId.trim();
    }

    public String getFollowDeviceId() {
        return followDeviceId;
    }

    public void setFollowDeviceId(String followDeviceId) {
        this.followDeviceId = followDeviceId == null ? null : followDeviceId.trim();
    }

    public String getFollowNetworkId() {
        return followNetworkId;
    }

    public void setFollowNetworkId(String followNetworkId) {
        this.followNetworkId = followNetworkId == null ? null : followNetworkId.trim();
    }

    public String getFollowIp() {
        return followIp;
    }

    public void setFollowIp(String followIp) {
        this.followIp = followIp == null ? null : followIp.trim();
    }

    public String getFollowPort() {
        return followPort;
    }

    public void setFollowPort(String followPort) {
        this.followPort = followPort == null ? null : followPort.trim();
    }

    public Boolean getActiveAuth() {
        return activeAuth;
    }

    public void setActiveAuth(Boolean activeAuth) {
        this.activeAuth = activeAuth;
    }

    public Boolean getIsOn() {
        return isOn;
    }

    public void setIsOn(Boolean isOn) {
        this.isOn = isOn;
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