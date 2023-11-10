package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SecurityServerInfoVo {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 安全服务器名称
     */
    private String serverName;

    /**
     * 通信ip
     */
    private String comIp;

    /**
     * 入网id
     */
    private String networkId;

    /**
     * 连接方式 1:内接 0:外接
     */
    private String connectType;

    /**
     * 状态 01:上线  02:下线
     */
    private String serverStatus;

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

    /**
     * 关联网卡信息列表
     */
    private List<NetworkCardVo> networkCardVos;
}
