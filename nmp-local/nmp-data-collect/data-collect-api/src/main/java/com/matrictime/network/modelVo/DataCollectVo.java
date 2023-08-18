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
     * 单位
     */
    private String unit;

    /**
     * 上传时间
     */
    private Date uploadTime;

}
