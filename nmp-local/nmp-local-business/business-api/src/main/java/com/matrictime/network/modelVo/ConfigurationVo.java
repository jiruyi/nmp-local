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
     * 路径类型 1：配置同步 2：信令启停 3：推送版本文件 4：启动版本文件
     */
    private String type;

    /**
     * 设备类型 1:基站 2 分发机 3 生成机 4 缓存机
     */
    private String deviceType;
}
