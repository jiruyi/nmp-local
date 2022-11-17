package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 话单上报表
 * @author   xxxx
 * @date   2022-11-16
 */
@Data
public class NmplBill {
    /**
     * 主键
     */
    private Long id;

    /**
     * 账单用户id
     */
    private String ownerId;

    /**
     * 流id
     */
    private String streamId;

    /**
     * 消耗流量
     */
    private String flowNumber;

    /**
     * 时长
     */
    private String timeLength;

    /**
     * 消耗密钥量
     */
    private String keyNum;

    /**
     * 杂糅因子
     */
    private String hybridFactor;

    /**
     * 创建时间
     */
    private Date uploadTime;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId == null ? null : streamId.trim();
    }

    public String getFlowNumber() {
        return flowNumber;
    }

    public void setFlowNumber(String flowNumber) {
        this.flowNumber = flowNumber == null ? null : flowNumber.trim();
    }

    public String getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(String timeLength) {
        this.timeLength = timeLength == null ? null : timeLength.trim();
    }

    public String getKeyNum() {
        return keyNum;
    }

    public void setKeyNum(String keyNum) {
        this.keyNum = keyNum == null ? null : keyNum.trim();
    }

    public String getHybridFactor() {
        return hybridFactor;
    }

    public void setHybridFactor(String hybridFactor) {
        this.hybridFactor = hybridFactor == null ? null : hybridFactor.trim();
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }
}