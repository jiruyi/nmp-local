package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2022/10/9.
 */
@Data
public class StaticRouteVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 路由Id
     */
    private String routeId;

    /**
     * 小区id
     */
    private String companyId;

    private String companyName;

    /**
     * 基站id
     */
    private String stationId;

    private String stationName;

    /**
     * 入网Id
     */
    private String networkId;

    /**
     * 设备入网Id
     */
    private String deviceId;

    /**
     * 服务名称
     */
    private String serverName;

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
