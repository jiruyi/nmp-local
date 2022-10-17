package com.matrictime.network.request;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2022/10/9.
 */
@Data
public class StaticRouteRequest extends BaseRequest{
    /**
     * 主键
     */
    private Long id;

    /**
     * 路由Id
     */
    private String routeId;

    /**
     * 基站id
     */
    private String stationId;

    /**
     * 设备入网码
     */
    private String networkId;

    /**
     * 服务器ip
     */
    private String serverIp;

    /**
     * ipV6
     */
    private String ipV6;

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
}
