package com.matrictime.network.req;

import lombok.Data;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
@Data
public class DnsManageRequest extends BaseRequest{
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
     * 隐私ip
     */
    private String lanIp;

    /**
     * 接收端口
     */
    private String receivePort;

    /**
     * 发送端口
     */
    private String sendPort;
}

