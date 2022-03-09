package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AddSignalReq implements Serializable {

    private static final long serialVersionUID = 5773604491924285572L;

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
}
