package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TotalLoadChangeReq implements Serializable {
    private static final long serialVersionUID = -611818778014663149L;

    /**
     * 小区id
     */
    private String roId;

    /**
     * 设备大类（0：基站 1：其他机）
     */
    private String bigType;

    /**
     * 设备类型
     * 基站 01:小区内基站 02:小区边界基站
     * 非基站 01:密钥分发机 02:生成机 03:缓存机
     */
    private String deviceType;
}
