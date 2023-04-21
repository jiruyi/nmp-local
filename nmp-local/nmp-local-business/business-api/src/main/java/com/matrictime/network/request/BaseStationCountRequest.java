package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
@Data
public class BaseStationCountRequest implements Serializable {
    private String currentConnectCount;

    private String stationId;

    private String deviceId;
}
