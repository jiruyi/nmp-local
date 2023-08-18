package com.matrictime.network.request;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/8/16.
 */
@Data
public class SystemHeartbeatRequest extends CollectBaseRequest {
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

}
