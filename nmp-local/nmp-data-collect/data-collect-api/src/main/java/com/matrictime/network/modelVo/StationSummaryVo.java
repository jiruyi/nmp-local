package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
@Data
public class StationSummaryVo {

    /**
     * 总和类型 01:小区内基站 02:小区边界基站 11:密钥中心 12:网络总数
     */
    private String sumType;

    /**
     * 小区唯一编号Id
     */
    private String companyNetworkId;

    /**
     * 每个小区各个设备类型总数
     */
    private String sumNumber;

    /**
     * 上报时间
     */
    private Date uploadTime;
}
