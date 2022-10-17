package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

@Data
public class NmplPcDataVo {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 基站设备id
     */
    private String stationId;

    /**
     * 一体机设备id
     */
    private String deviceId;

    /**
     * 一体机设备入网码
     */
    private String pcNetworkId;

    /**
     * 设备状态 1:接入  2:未接入
     */
    private Byte status;

    /**
     * 上行消耗密钥量(单位byte)
     */
    private double upKeyNum;

    /**
     * 下行消耗密钥量(单位byte)
     */
    private double downKeyNum;

    /**
     * 上报时间
     */
    private Date reportTime;
}
