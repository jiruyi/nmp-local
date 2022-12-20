package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 密钥信息数据表
 * @author   hx
 * @date   2022-12-20
 */
@Data
public class NmpKeyInfo {
    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 数据值（单位byte）
     */
    private Long dataValue;

    /**
     * 数据类型（1000：剩余上行密钥量 1001：已使用上行密钥量 2000：剩余下行密钥量 2001：已使用下行密钥量）
     */
    private String dataType;

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

    public Long getDataValue() {
        return dataValue;
    }

    public void setDataValue(Long dataValue) {
        this.dataValue = dataValue;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
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