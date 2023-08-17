package com.matrictime.network.request;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/8/16.
 */
@Data
public class TerminalUserRequest extends CollectBaseRequest {
    /**
     * 终端设备Id
     */
    private String terminalNetworkId;

    /**
     * 所属设备Id
     */
    private String parentDeviceId;

    /**
     * 用户状态 01:密钥匹配  02:注册  03:上线 04:下线 05:注销
     */
    private String terminalStatus;

    /**
     * 上报时间
     */
    private Date uploadTime;

    /**
     * 用户类型 01:一体机  02:安全服务器
     */
    private String userType;
}
