package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/11/6.
 */
@Data
public class DnsManageVo {
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


    /**
     * 创建时间
     */
    private Date createTime;
}
