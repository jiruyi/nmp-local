package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 基站下一体机信息上报表
 * @author   xxxx
 * @date   2023-08-29
 */
@Data
public class NmplTerminalData {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 一体机设备id
     */
    private String terminalNetworkId;

    /**
     * 基站设备id
     */
    private String parentId;

    /**
     * 数据类型 01:剩余 02:补充 03:使用
     */
    private String dataType;

    /**
     * 上行密钥量
     */
    private Long upValue;

    /**
     * 下行密钥量
     */
    private Long downValue;

    /**
     * 一体机ip
     */
    private String terminalIp;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerminalNetworkId() {
        return terminalNetworkId;
    }

    public void setTerminalNetworkId(String terminalNetworkId) {
        this.terminalNetworkId = terminalNetworkId == null ? null : terminalNetworkId.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public Long getUpValue() {
        return upValue;
    }

    public void setUpValue(Long upValue) {
        this.upValue = upValue;
    }

    public Long getDownValue() {
        return downValue;
    }

    public void setDownValue(Long downValue) {
        this.downValue = downValue;
    }

    public String getTerminalIp() {
        return terminalIp;
    }

    public void setTerminalIp(String terminalIp) {
        this.terminalIp = terminalIp == null ? null : terminalIp.trim();
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