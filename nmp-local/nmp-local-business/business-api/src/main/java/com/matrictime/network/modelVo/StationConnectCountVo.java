package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/8/7.
 */
@Data
public class StationConnectCountVo {
    /**
     * 设备Id
     */
    private String stationId;

    /**
     * 当前用户数
     */
    private String currentConnectCount;

    /**
     * 上报时间
     */
    private Date uploadTime;

}
