package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProxyBusinessRouteVo implements Serializable {
    private static final long serialVersionUID = 232875205046760889L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 路由Id
     */
    private String routeId;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 设备入网码
     */
    private String networkId;

    /**
     * ip
     */
    private String ip;

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
     * ip_v6
     */
    private String ipV6;

    private byte[] byteNetworkId;
}
