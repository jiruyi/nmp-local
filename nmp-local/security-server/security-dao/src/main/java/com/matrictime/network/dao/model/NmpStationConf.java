package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 基站信息配置表
 * @author   hx
 * @date   2022-12-20
 */
@Data
public class NmpStationConf {
    /**
     * 配置ID
     */
    private Long id;

    /**
     * 主基站接入方式（1：固定接入 2：动态接入）
     */
    private String mainAccessType;

    /**
     * 主基站通信ip
     */
    private String mainCommIp;

    /**
     * 主基站域名ip
     */
    private String mainDomainName;

    /**
     * 主基站端口
     */
    private String mainPort;

    /**
     * 备用基站接入方式（1：固定接入 2：动态接入）
     */
    private String spareAccessType;

    /**
     * 备用基站通信ip
     */
    private String spareCommIp;

    /**
     * 备用基站域名ip
     */
    private String spareDomainName;

    /**
     * 备用基站端口
     */
    private String sparePort;

    /**
     * 删除标志（1代表存在 0代表删除）
     */
    private Boolean isExist;

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
     * 备注
     */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMainAccessType() {
        return mainAccessType;
    }

    public void setMainAccessType(String mainAccessType) {
        this.mainAccessType = mainAccessType == null ? null : mainAccessType.trim();
    }

    public String getMainCommIp() {
        return mainCommIp;
    }

    public void setMainCommIp(String mainCommIp) {
        this.mainCommIp = mainCommIp == null ? null : mainCommIp.trim();
    }

    public String getMainDomainName() {
        return mainDomainName;
    }

    public void setMainDomainName(String mainDomainName) {
        this.mainDomainName = mainDomainName == null ? null : mainDomainName.trim();
    }

    public String getMainPort() {
        return mainPort;
    }

    public void setMainPort(String mainPort) {
        this.mainPort = mainPort == null ? null : mainPort.trim();
    }

    public String getSpareAccessType() {
        return spareAccessType;
    }

    public void setSpareAccessType(String spareAccessType) {
        this.spareAccessType = spareAccessType == null ? null : spareAccessType.trim();
    }

    public String getSpareCommIp() {
        return spareCommIp;
    }

    public void setSpareCommIp(String spareCommIp) {
        this.spareCommIp = spareCommIp == null ? null : spareCommIp.trim();
    }

    public String getSpareDomainName() {
        return spareDomainName;
    }

    public void setSpareDomainName(String spareDomainName) {
        this.spareDomainName = spareDomainName == null ? null : spareDomainName.trim();
    }

    public String getSparePort() {
        return sparePort;
    }

    public void setSparePort(String sparePort) {
        this.sparePort = sparePort == null ? null : sparePort.trim();
    }

    public Boolean getIsExist() {
        return isExist;
    }

    public void setIsExist(Boolean isExist) {
        this.isExist = isExist;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}