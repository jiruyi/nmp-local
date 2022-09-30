package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author by wangqiang
 * @date 2022/9/22.
 */
@Data
public class BaseStationInfoRequest implements Serializable {
    /**
     * 设备id
     */
    private String stationId;
}
