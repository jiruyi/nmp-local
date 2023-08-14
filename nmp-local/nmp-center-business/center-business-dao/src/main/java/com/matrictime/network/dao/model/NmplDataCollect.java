package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 小区流量收集表
 * @author   wangqiang
 * @date   2023-08-07
 */
@Data
public class NmplDataCollect {
    /**
     * 主键
     */
    private Long id;

    /**
     * 每个小区每个流量类型总和
     */
    private String sumNumber;

    /**
     * 小区唯一编号Id
     */
    private String companyNetworkId;

    /**
     * 收集项编号
     */
    private String dataItemCode;

    /**
     * 单位
     */
    private String unit;

    /**
     * 创建时间
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSumNumber() {
        return sumNumber;
    }

    public void setSumNumber(String sumNumber) {
        this.sumNumber = sumNumber == null ? null : sumNumber.trim();
    }

    public String getCompanyNetworkId() {
        return companyNetworkId;
    }

    public void setCompanyNetworkId(String companyNetworkId) {
        this.companyNetworkId = companyNetworkId == null ? null : companyNetworkId.trim();
    }

    public String getDataItemCode() {
        return dataItemCode;
    }

    public void setDataItemCode(String dataItemCode) {
        this.dataItemCode = dataItemCode == null ? null : dataItemCode.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
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