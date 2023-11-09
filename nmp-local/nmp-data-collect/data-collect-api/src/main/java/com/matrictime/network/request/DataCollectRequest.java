package com.matrictime.network.request;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/8/16.
 */
@Data
public class DataCollectRequest extends CollectBaseRequest{

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
     * 创建时间
     */
    private Date uploadTime;
}
