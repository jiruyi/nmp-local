package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LinkDevice implements Serializable {

    private static final long serialVersionUID = -732603081324223026L;

    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 公网Ip
     */
    private String publicNetworkIp;

    /**
     * 公网端口
     */
    private String publicNetworkPort;

    /**
     * 内网Ip
     */
    private String lanIp;

    /**
     * 内网端口
     */
    private String lanPort;
}
