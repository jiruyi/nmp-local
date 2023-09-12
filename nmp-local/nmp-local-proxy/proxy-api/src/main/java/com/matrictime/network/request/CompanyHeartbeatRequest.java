package com.matrictime.network.request;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/9/12.
 */
@Data
public class CompanyHeartbeatRequest {

    /**
     * 来源Id
     */
    private String sourceNetworkId;

    /**
     * 目标Id
     */
    private String targetNetworkId;

    /**
     * 连接状态 01:通  02:不通
     */
    private String status;

    /**
     * 上行流量
     */
    private String upValue;

    /**
     * 下行流量
     */
    private String downValue;

    /**
     * 上报时间
     */
    private Date uploadTime;

}
