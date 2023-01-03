package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;
@Data
public class CommAreaConfVo {
    /**
     * 配置ID
     */
    private Long id;

    /**
     * 通信ip
     */
    private String commIp;

    /**
     * 端口
     */
    private String port;

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
