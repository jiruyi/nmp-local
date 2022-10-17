package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2022/9/28.
 */
@Data
public class InternetRouteVo {
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
     * 边界基站ip
     */
    private String boundaryStationIp;

    /**
     * ipV6
     */
    private String ipV6;

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
}
