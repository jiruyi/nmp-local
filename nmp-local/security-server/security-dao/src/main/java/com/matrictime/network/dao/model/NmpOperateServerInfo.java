package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 操作服务器信息表
 * @author   xxxx
 * @date   2023-02-03
 */
@Data
public class NmpOperateServerInfo {
    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 通知类型（1000：启动配置 1001：更新密钥）
     */
    private Short operateType;

    /**
     * 操作状态（1000：待执行 1001：执行结束）
     */
    private Short operateStatus;

    /**
     * 操作进度（0~100）
     */
    private Short operateRate;

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

    public Short getOperateType() {
        return operateType;
    }

    public void setOperateType(Short operateType) {
        this.operateType = operateType;
    }

    public Short getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(Short operateStatus) {
        this.operateStatus = operateStatus;
    }

    public Short getOperateRate() {
        return operateRate;
    }

    public void setOperateRate(Short operateRate) {
        this.operateRate = operateRate;
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