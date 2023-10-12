package com.matrictime.network.request;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/8/7.
 */
@Data
public class DataCollectRequest extends CenterBaseRequest{

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
