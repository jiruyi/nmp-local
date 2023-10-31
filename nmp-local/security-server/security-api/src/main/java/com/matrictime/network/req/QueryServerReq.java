package com.matrictime.network.req;

import lombok.Data;

@Data
public class QueryServerReq extends BaseRequest{

    /**
     * 安全服务器名称
     */
    private String serverName;

    /**
     * ip
     */
    private String ip;

    /**
     * 入网id
     */
    private String networkId;

    /**
     * 网卡类型（1：物理网卡 2：虚拟网卡）
     */
    private String netCardType;

    /**
     * 适配器名称
     */
    private String adapterName;
}
