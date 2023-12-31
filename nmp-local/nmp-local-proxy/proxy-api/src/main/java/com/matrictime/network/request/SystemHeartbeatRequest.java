package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
@Data
public class SystemHeartbeatRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 来源Id
     */
    private String sourceId;

    /**
     * 目标Id
     */
    private String targetId;

    /**
     * 连接状态 01:通  02:不通
     */
    private String status;

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
}
