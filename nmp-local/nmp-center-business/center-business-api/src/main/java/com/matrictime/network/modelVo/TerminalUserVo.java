package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/8/14.
 */
@Data
public class TerminalUserVo {

    /**
     * 用户状态 01:密钥匹配  02:注册  03:上线 04:下线 05:注销
     */
    private String terminalStatus;

    /**
     * 小区唯一编号Id
     */
    private String companyNetworkId;

    /**
     * 用户类型 01:一体机  02:安全服务器
     */
    private String userType;

    /**
     * 每个小区用户状态总数
     */
    private String sumNumber;

    /**
     * 上报时间
     */
    private Date uploadTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
