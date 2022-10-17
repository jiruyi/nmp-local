package com.matrictime.network.request;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2022/9/28.
 */
@Data
public class BusinessRouteRequest extends BaseRequest{
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
