package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2022/9/28.
 */
@Data
public class BusinessRouteVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 路由Id
     */
    private String routeId;

    /**
     * 业务类型 11:根密钥中心 31:指控中心 41:计费中心
     */
    private String businessType;

    /**
     * 设备入网码
     */
    private String networkId;

    /**
     * ip_v4
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

    /**
     * 设备入网码
     */
    private byte[] byteNetworkId;
}
