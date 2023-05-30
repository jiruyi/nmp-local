package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author by wangqiang
 * @date 2023/5/23.
 */
@Data
public class CurrentCountRequest implements Serializable {

    private BaseStationCurrentRequest baseStationCurrentRequest;

    private DeviceCurrentRequest deviceCurrentRequest;
}
