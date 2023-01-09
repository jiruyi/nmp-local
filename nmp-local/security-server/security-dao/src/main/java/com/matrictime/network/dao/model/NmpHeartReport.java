package com.matrictime.network.dao.model;

import java.util.Date;
import lombok.Data;

/**
 * 安全服务器心跳上报
 * @author   hx
 * @date   2023-01-09
 */
@Data
public class NmpHeartReport {
    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 状态(1:正常)
     */
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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