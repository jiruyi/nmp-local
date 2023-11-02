package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
@Data
public class CaManageVo {
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
     * 创建时间
     */
    private Date createTime;
}
