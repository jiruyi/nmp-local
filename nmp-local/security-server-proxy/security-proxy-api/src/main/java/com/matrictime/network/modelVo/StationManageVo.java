package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
@Data
public class StationManageVo {

    /**
     * 安全服务器名称
     */
    private String serverName;

    /**
     * 通信ip
     */
    private String comIp;

    /**
     * 入网id
     */
    private String networkId;

    /**
     * 连接方式 1:内接 0:外接
     */
    private String connectType;

    /**
     * 基站类型 00:基站 30:备用基站
     */
    private String stationType;

    /**
     * 接入方式 01:固定接入 02:动态接入
     */
    private String accessMethod;

    /**
     * 域名
     */
    private String domainName;

    /**
     * 通信ip
     */
    private String stationIp;

    /**
     * 通信port
     */
    private String stationPort;

    /**
     * 创建时间
     */
    private Date createTime;
}
