package com.matrictime.network.req;

import lombok.Data;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
@Data
public class StationManageRequest extends BaseRequest{

    /**
     * 主键
     */
    private Long id;

    /**
     * 入网id
     */
    private String networkId;

    /**
     * 安全服务器通信ip
     */
    private String comIp;

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
     * 下载密钥端口
     */
    private String keyPort;
}
