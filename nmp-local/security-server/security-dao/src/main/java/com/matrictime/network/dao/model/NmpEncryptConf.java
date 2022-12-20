package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 加密配置表
 * @author   hx
 * @date   2022-12-20
 */
@Data
public class NmpEncryptConf {
    /**
     * 配置ID
     */
    private Long id;

    /**
     * 上行加密比例（1：1:1 2：1:8 3：1:16 4：1:32 5：1:64 6：1:128）
     */
    private String upEncryptRatio;

    /**
     * 上行加密扩展算法（1：1:1 2：1:8 3：1:16 4：1:32 5：1:64 6：1:128）
     */
    private String upExtendAlgorithm;

    /**
     * 上行加密方式（1：全加密 2：负载加密 3：不加密）
     */
    private String upEncryptType;

    /**
     * 上行加密算法（1：算法A）
     */
    private String upEncryptAlgorithm;

    /**
     * 上行密钥最大值
     */
    private Long upMaxValue;

    /**
     * 上行密钥预警值
     */
    private Long upWarnValue;

    /**
     * 上行密钥最小值
     */
    private Long upMinValue;

    /**
     * 下行密钥最大值
     */
    private Long downMaxValue;

    /**
     * 下行密钥预警值
     */
    private Long downWarnValue;

    /**
     * 下行密钥最小值
     */
    private Long downMinValue;

    /**
     * 随机数最大值
     */
    private Long randomMaxValue;

    /**
     * 随机数预警值
     */
    private Long randomWarnValue;

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

    public String getUpEncryptRatio() {
        return upEncryptRatio;
    }

    public void setUpEncryptRatio(String upEncryptRatio) {
        this.upEncryptRatio = upEncryptRatio == null ? null : upEncryptRatio.trim();
    }

    public String getUpExtendAlgorithm() {
        return upExtendAlgorithm;
    }

    public void setUpExtendAlgorithm(String upExtendAlgorithm) {
        this.upExtendAlgorithm = upExtendAlgorithm == null ? null : upExtendAlgorithm.trim();
    }

    public String getUpEncryptType() {
        return upEncryptType;
    }

    public void setUpEncryptType(String upEncryptType) {
        this.upEncryptType = upEncryptType == null ? null : upEncryptType.trim();
    }

    public String getUpEncryptAlgorithm() {
        return upEncryptAlgorithm;
    }

    public void setUpEncryptAlgorithm(String upEncryptAlgorithm) {
        this.upEncryptAlgorithm = upEncryptAlgorithm == null ? null : upEncryptAlgorithm.trim();
    }

    public Long getUpMaxValue() {
        return upMaxValue;
    }

    public void setUpMaxValue(Long upMaxValue) {
        this.upMaxValue = upMaxValue;
    }

    public Long getUpWarnValue() {
        return upWarnValue;
    }

    public void setUpWarnValue(Long upWarnValue) {
        this.upWarnValue = upWarnValue;
    }

    public Long getUpMinValue() {
        return upMinValue;
    }

    public void setUpMinValue(Long upMinValue) {
        this.upMinValue = upMinValue;
    }

    public Long getDownMaxValue() {
        return downMaxValue;
    }

    public void setDownMaxValue(Long downMaxValue) {
        this.downMaxValue = downMaxValue;
    }

    public Long getDownWarnValue() {
        return downWarnValue;
    }

    public void setDownWarnValue(Long downWarnValue) {
        this.downWarnValue = downWarnValue;
    }

    public Long getDownMinValue() {
        return downMinValue;
    }

    public void setDownMinValue(Long downMinValue) {
        this.downMinValue = downMinValue;
    }

    public Long getRandomMaxValue() {
        return randomMaxValue;
    }

    public void setRandomMaxValue(Long randomMaxValue) {
        this.randomMaxValue = randomMaxValue;
    }

    public Long getRandomWarnValue() {
        return randomWarnValue;
    }

    public void setRandomWarnValue(Long randomWarnValue) {
        this.randomWarnValue = randomWarnValue;
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