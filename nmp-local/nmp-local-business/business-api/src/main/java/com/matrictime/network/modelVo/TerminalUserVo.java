package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/4/18.
 */
@Data
public class TerminalUserVo implements Serializable {
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
