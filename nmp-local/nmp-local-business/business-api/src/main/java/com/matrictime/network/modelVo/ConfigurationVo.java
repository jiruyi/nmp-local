package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConfigurationVo implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * 内网Ip
     */
    private String realIp;

    /**
     * 内网端口
     */
    private String realPort;

    /**
     * 路径
     */
    private String url;

    /**
     * 路径类型
     */
    private String type;
}
