package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProxyInternetRouteVo implements Serializable {
    private static final long serialVersionUID = 8274628004414160297L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 路由Id
     */
    private String routeId;

    /**
     * 设备入网码
     */
    private String networkId;

    /**
     * 边界基站ip_v4
     */
    private String boundaryStationIp;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 1:存在 0:删除
     */
    private Boolean isExist;

    /**
     * 边界基站ip_v6
     */
    private String ipV6;

    /**
     * 下一条入网码
     */
    private String nextNetworkId;

    /**
     * 跳数
     */
    private String hopCount;

    /**
     * 小区名称
     */
    private String companyName;

    /**
     * 小区id
     */
    private String companyId;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 设备入网码
     */
    private byte[] byteNetworkId;
}
