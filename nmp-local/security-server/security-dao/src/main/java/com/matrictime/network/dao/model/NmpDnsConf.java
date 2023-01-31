package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * dns信息配置表
 * @author   xxxx
 * @date   2023-01-13
 */
@Data
public class NmpDnsConf {
    /**
     * 配置ID
     */
    private Long id;

    /**
     * 通信ip
     */
    private String commIp;

    /**
     * 隐私ip
     */
    private String secretIp;

    /**
     * 入网id
     */
    private String networkId;

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

    /**
     * 入网码前缀
     */
    private Long prefixNetworkId;

    /**
     * 入网码后缀
     */
    private Long suffixNetworkId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommIp() {
        return commIp;
    }

    public void setCommIp(String commIp) {
        this.commIp = commIp == null ? null : commIp.trim();
    }

    public String getSecretIp() {
        return secretIp;
    }

    public void setSecretIp(String secretIp) {
        this.secretIp = secretIp == null ? null : secretIp.trim();
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId == null ? null : networkId.trim();
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

    public Long getPrefixNetworkId() {
        return prefixNetworkId;
    }

    public void setPrefixNetworkId(Long prefixNetworkId) {
        this.prefixNetworkId = prefixNetworkId;
    }

    public Long getSuffixNetworkId() {
        return suffixNetworkId;
    }

    public void setSuffixNetworkId(Long suffixNetworkId) {
        this.suffixNetworkId = suffixNetworkId;
    }
}