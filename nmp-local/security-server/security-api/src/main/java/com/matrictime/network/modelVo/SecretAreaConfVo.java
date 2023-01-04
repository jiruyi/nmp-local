package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;
@Data
public class SecretAreaConfVo {
    /**
     * 配置ID
     */
    private Long id;

    /**
     * 当前ip
     */
    private String localIp;

    /**
     * 入网id
     */
    private String networkId;

    /**
     * 网卡类型（1：物理网卡 2：虚拟网卡）
     */
    private String netCardType;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 输入ip
     */
    private String inputIp;

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
