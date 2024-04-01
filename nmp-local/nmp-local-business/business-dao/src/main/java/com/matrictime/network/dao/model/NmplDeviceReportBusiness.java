package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 数据采集节点业务上报配置表
 * @author   hexu
 * @date   2024-04-01
 */
@Data
public class NmplDeviceReportBusiness {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 业务编码
     */
    private String businessCode;

    /**
     * 业务配置值
     */
    private String businessValue;

    /**
     * 上报时间
     */
    private Date reportTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 状态 true:存在(1)  false:删除(0)
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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName == null ? null : businessName.trim();
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode == null ? null : businessCode.trim();
    }

    public String getBusinessValue() {
        return businessValue;
    }

    public void setBusinessValue(String businessValue) {
        this.businessValue = businessValue == null ? null : businessValue.trim();
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Boolean getIsExist() {
        return isExist;
    }

    public void setIsExist(Boolean isExist) {
        this.isExist = isExist;
    }
}