package com.matrictime.network.request;

import lombok.Data;

@Data
public class QueryDeviceForLinkReq {

    /**
     * 设备类型 01:小区内接入基站 02:小区边界基站 11:密钥中心 12:生成机 13:缓存机 20:采集设备 21:指控中心
     */
    private String deviceType;
}
