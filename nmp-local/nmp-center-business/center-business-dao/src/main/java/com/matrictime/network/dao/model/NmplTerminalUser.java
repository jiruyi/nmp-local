package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 小区用户汇总表
 * @author   wq
 * @date   2023-08-14
 */
@Data
public class NmplTerminalUser {
    /**
     * 用户状态 01:密钥匹配  02:注册  03:上线 04:下线 05:注销
     */
    private String terminalStatus;

    /**
     * 小区唯一编号Id
     */
    private String companyNetworkId;

    /**
     * 用户类型 01:一体机  02:安全服务器
     */
    private String userType;

    /**
     * 每个小区用户状态总数
     */
    private String sumNumber;

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

    public String getTerminalStatus() {
        return terminalStatus;
    }

    public void setTerminalStatus(String terminalStatus) {
        this.terminalStatus = terminalStatus == null ? null : terminalStatus.trim();
    }

    public String getCompanyNetworkId() {
        return companyNetworkId;
    }

    public void setCompanyNetworkId(String companyNetworkId) {
        this.companyNetworkId = companyNetworkId == null ? null : companyNetworkId.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getSumNumber() {
        return sumNumber;
    }

    public void setSumNumber(String sumNumber) {
        this.sumNumber = sumNumber == null ? null : sumNumber.trim();
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