package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class ProxyStaticRouteVo implements Serializable {
    private static final long serialVersionUID = 7088210297189127481L;

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
     * 服务器ip_v6
     */
    private String serverIp;

    /**
     * 删除标志（1代表存在 0代表删除）
     */
    private Boolean isExist;

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
     * 基站id
     */
    private String stationId;

    /**
     * 服务器ip_v6
     */
    private String ipV6;

    /**
     * 小区名称
     */
    private String companyName;

    /**
     * 小区id
     */
    private String companyId;

    /**
     * 服务名称
     */
    private String serverName;

    /**
     * 接入基站名称
     */
    private String stationName;

    /**
     * 设备入网码
     */
    private byte[] byteNetworkId;
}
