package com.matrictime.network.request;

import lombok.Data;

import java.util.Date;

/**
 * @author by wangqiang
 * @date 2023/8/7.
 */
@Data
public class StationConnectCountRequest extends BaseRequest{

    /**
     * 主键
     */
    private Long id;

    /**
     * 设备Id
     */
    private String stationId;

    /**
     * 当前用户数
     */
    private String currentConnectCount;

    /**
     * 小区id
     */
    private String relationOperatorId;

    /**
     * 设备类型
     */
    private String stationType;

    /**
     * 上报时间
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
}
