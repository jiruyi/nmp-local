package com.matrictime.network.request;

import lombok.Data;

/**
 * @author by wangqiang
 * @date 2023/10/27.
 */
@Data
public class IpManageRequest {
    /**
     * 主键
     */
    private Long id;

    /**
     * 管理类型 01:基站 02:CA 03:DNS
     */
    private String manageType;

    /**
     * 入网id
     */
    private String networkId;

    /**
     * 服务器名称
     */
    private String securitySeverName;

    /**
     * 安全服务器ip
     */
    private String securitySeverIp;

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
    private String publicIp;

    /**
     * 通信port
     */
    private String publicPort;

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
     * 1:存在 0:删除
     */
    private Boolean isExist;

}
