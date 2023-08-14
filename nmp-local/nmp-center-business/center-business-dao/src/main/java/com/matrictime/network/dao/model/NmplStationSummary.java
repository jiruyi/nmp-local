package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 小区汇总表
 * @author   wq
 * @date   2023-08-14
 */
@Data
public class NmplStationSummary {
    /**
     * 总和类型 01:小区内基站 02:小区边界基站 11:密钥中心 12:网络总数
     */
    private String sumType;

    /**
     * 小区唯一编号Id
     */
    private String companyNetworkId;

    /**
     * 每个小区各个设备类型总数
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

    public String getSumType() {
        return sumType;
    }

    public void setSumType(String sumType) {
        this.sumType = sumType == null ? null : sumType.trim();
    }

    public String getCompanyNetworkId() {
        return companyNetworkId;
    }

    public void setCompanyNetworkId(String companyNetworkId) {
        this.companyNetworkId = companyNetworkId == null ? null : companyNetworkId.trim();
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