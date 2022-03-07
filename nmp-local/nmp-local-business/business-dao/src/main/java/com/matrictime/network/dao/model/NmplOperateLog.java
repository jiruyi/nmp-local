package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 
 * @author   hexu
 * @date   2022-03-03
 */
@Data
public class NmplOperateLog {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 1:网管pc 2：基站
     */
    private Byte channelType;

    /**
     * 操作模块
     */
    private String operModul;

    /**
     * 请求的url
     */
    private String operUrl;

    /**
     * 操作类型(新增 修改 编辑等)
     */
    private String operType;

    /**
     * 操作描述
     */
    private String operDesc;

    /**
     * 请求参数
     */
    private String operRequParam;

    /**
     * 服务名字
     */
    private String operRespParam;

    /**
     * 操作方法
     */
    private String operMethod;

    /**
     * 操作人id
     */
    private String operUserId;

    /**
     * 操作人姓名
     */
    private String operUserName;

    /**
     * 1 成功  2 失败
     */
    private Boolean isSuccess;

    /**
     * 操作ip
     */
    private String sourceIp;

    /**
     * 操作级别(1:高危 2 危险 3 警告 4 常规)
     */
    private String operLevel;

    /**
     * 操作时间
     */
    private Date operTime;

    /**
     * 备注
     */
    private String remark;

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

    public Byte getChannelType() {
        return channelType;
    }

    public void setChannelType(Byte channelType) {
        this.channelType = channelType;
    }

    public String getOperModul() {
        return operModul;
    }

    public void setOperModul(String operModul) {
        this.operModul = operModul == null ? null : operModul.trim();
    }

    public String getOperUrl() {
        return operUrl;
    }

    public void setOperUrl(String operUrl) {
        this.operUrl = operUrl == null ? null : operUrl.trim();
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType == null ? null : operType.trim();
    }

    public String getOperDesc() {
        return operDesc;
    }

    public void setOperDesc(String operDesc) {
        this.operDesc = operDesc == null ? null : operDesc.trim();
    }

    public String getOperRequParam() {
        return operRequParam;
    }

    public void setOperRequParam(String operRequParam) {
        this.operRequParam = operRequParam == null ? null : operRequParam.trim();
    }

    public String getOperRespParam() {
        return operRespParam;
    }

    public void setOperRespParam(String operRespParam) {
        this.operRespParam = operRespParam == null ? null : operRespParam.trim();
    }

    public String getOperMethod() {
        return operMethod;
    }

    public void setOperMethod(String operMethod) {
        this.operMethod = operMethod == null ? null : operMethod.trim();
    }

    public String getOperUserId() {
        return operUserId;
    }

    public void setOperUserId(String operUserId) {
        this.operUserId = operUserId == null ? null : operUserId.trim();
    }

    public String getOperUserName() {
        return operUserName;
    }

    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName == null ? null : operUserName.trim();
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp == null ? null : sourceIp.trim();
    }

    public String getOperLevel() {
        return operLevel;
    }

    public void setOperLevel(String operLevel) {
        this.operLevel = operLevel == null ? null : operLevel.trim();
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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