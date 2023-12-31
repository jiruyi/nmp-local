package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 静态路由
 * @author   hx
 * @date   2022-10-11
 */
@Data
public class NmplStaticRouteVo implements Serializable {

    private static final long serialVersionUID = -6296136216387922768L;

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