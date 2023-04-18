package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;
@Data
public class DataCollectVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备名字
     */
    private String deviceName;

    /**
     * 设备ip
     */
    private String deviceIp;

    /**
     * 设备类别(01接入基站、02边界基站、11密钥中心、12生成机、13缓存机)
     */
    private String deviceType;

    /**
     * 统计项名
     */
    private String dataItemName;

    /**
     * 收集项编号
     */
    private String dataItemCode;

    /**
     * 值
     */
    private String dataItemValue;

    /**
     * 单位
     */
    private String unit;

    /**
     * 创建时间
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
     * 设备状态
     */
    private String stationStatus;
}
