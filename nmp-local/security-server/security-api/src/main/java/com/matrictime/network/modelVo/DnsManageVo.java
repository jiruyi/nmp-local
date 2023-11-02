package com.matrictime.network.modelVo;

import lombok.Data;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
@Data
public class DnsManageVo {
    /**
     * 主键
     */
    private Long id;


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
