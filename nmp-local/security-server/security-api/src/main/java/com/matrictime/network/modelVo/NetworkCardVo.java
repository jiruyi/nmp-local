package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

/**
 * 安全服务器关联网卡信息表
 * @author   xxxx
 * @date   2023-11-01
 */
@Data
public class NetworkCardVo {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 入网id
     */
    private String networkId;

    /**
     * 网卡类型（1：物理网卡 2：虚拟网卡）
     */
    private String netCardType;

    /**
     * 适配器名称
     */
    private String adapterName;

    /**
     * ipv4
     */
    private String ipv4;

    /**
     * ipv6
     */
    private String ipv6;

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
     * 备注
     */
    private String remark;

}