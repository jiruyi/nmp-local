package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;
@Data
public class StationConfVo {
    /**
     * 配置ID
     */
    private Long id;

    /**
     * 主基站接入方式（1：固定接入 2：动态接入）
     */
    private String mainAccessType;

    /**
     * 主基站通信ip
     */
    private String mainCommIp;

    /**
     * 主基站域名ip
     */
    private String mainDomainName;

    /**
     * 主基站端口
     */
    private String mainPort;

    /**
     * 备用基站接入方式（1：固定接入 2：动态接入）
     */
    private String spareAccessType;

    /**
     * 备用基站通信ip
     */
    private String spareCommIp;

    /**
     * 备用基站域名ip
     */
    private String spareDomainName;

    /**
     * 备用基站端口
     */
    private String sparePort;

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
