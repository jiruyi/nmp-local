package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/8/14.
 */
@Data
public class CompanyHeartbeatVo {

    /**
     * 来源Id
     */
    private String sourceId;

    /**
     * 目标Id
     */
    private String targetId;

    /**
     * 小区唯一编号Id
     */
    private String companyNetworkId;

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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
