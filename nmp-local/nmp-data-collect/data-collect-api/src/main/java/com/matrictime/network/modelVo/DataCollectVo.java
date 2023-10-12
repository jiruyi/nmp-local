package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/8/16.
 */
@Data
public class DataCollectVo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 每个小区每个流量类型总和
     */
    private String sumNumber;

    /**
     * 小区唯一编号Id
     */
    private String companyNetworkId;

    /**
     * 收集项编号
     */
    private String dataItemCode;

    /**
     * 设备类别(01接入基站、02边界基站、11密钥中心、12生成机、13缓存机)
     */
    private String deviceType;

    /**
     * 单位
     */
    private String unit;

    /**
     * 上传时间
     */
    private Date uploadTime;

}
