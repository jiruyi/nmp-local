package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/8/18.
 */
@Data
public class CompanyHeartbeatVo {

    /**
     * 来源Id
     */
    private String sourceCompanyNetworkId;

    /**
     * 目标Id
     */
    private String targetCompanyNetworkId;

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
