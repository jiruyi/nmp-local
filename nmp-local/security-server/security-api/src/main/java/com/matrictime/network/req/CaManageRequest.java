package com.matrictime.network.req;

import lombok.Data;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
@Data
public class CaManageRequest extends BaseRequest{

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
     * 安全服务器名称
     */
    private String serverName;


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
    private String publicIp;

    /**
     * 通信port
     */
    private String publicPort;

    /**
     * 隐私ip
     */
    private String lanIp;

}
