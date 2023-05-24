package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author by wangqiang
 * @date 2023/5/23.
 */
@Data
public class BaseStationCurrentRequest implements Serializable {

    private String stationId;

    private String currentConnectCount;
}
