package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class NmplSignalVo implements Serializable {
    private static final long serialVersionUID = -4723266384099789586L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * 信令名称
     */
    private String signalName;

    /**
     * 发送方ip
     */
    private String sendIp;

    /**
     * 接收方ip
     */
    private String receiveIp;

    /**
     * 信令内容
     */
    private String signalContent;

    /**
     * 业务模块
     */
    private String businessModule;

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

    /**
     * 状态true:存在(1)  false:删除(0)
     */
    private Boolean isExist;

    /**
     * 用户id
     */
    private String userId;
}
