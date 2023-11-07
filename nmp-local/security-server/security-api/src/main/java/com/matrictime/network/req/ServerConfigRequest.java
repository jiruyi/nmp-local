package com.matrictime.network.req;

import lombok.Data;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
@Data
public class ServerConfigRequest {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备入网码
     */
    private String networkId;

    /**
     * 安全服务器通信ip
     */
    private String comIp;

    /**
     * 配置名称 50:加密比例 51:扩展算法 52:加密方式 53:加密算法 54:上行密钥最大值
     * 55:上行密钥预警值 56:上行密钥最小值 57:下行密钥最大值 58:下行密钥预警值 59:下行密钥最小值
     */
    private String configCode;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 默认值
     */
    private String defaultValue;
}
