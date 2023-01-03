package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;
@Data
public class CaConfVo {
    /**
     * 配置ID
     */
    private Long id;

    /**
     * 接入方式（1：固定接入 2：动态接入）
     */
    private String accessType;

    /**
     * 通信ip
     */
    private String commIp;

    /**
     * 域名ip
     */
    private String domainName;

    /**
     * 端口
     */
    private String port;

    /**
     * 隐私ip
     */
    private String secretIp;

    /**
     * 入网id
     */
    private String networkId;

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
