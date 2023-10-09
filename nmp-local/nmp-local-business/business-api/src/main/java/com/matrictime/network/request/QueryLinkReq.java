package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryLinkReq extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 6531173522863837819L;

    /**
     * 链路名称
     */
    private String linkName;

    /**
     * 本端设备类型 01:小区内基站 02:小区边界基站 11:密钥中心 12:生成机 13:缓存机 20:采集设备 21:指控中心
     */
    private String mainDeviceType;

    /**
     * 本端设备名称
     */
    private String mainDeviceName;

    /**
     * 本端设备id
     */
    private String mainDeviceId;

    /**
     * 对端设备id
     */
    private String followDeviceId;

    /**
     * 对端设备IP
     */
    private String followIp;

    /**
     * 对端设备名称
     */
    private String followDeviceName;
}
