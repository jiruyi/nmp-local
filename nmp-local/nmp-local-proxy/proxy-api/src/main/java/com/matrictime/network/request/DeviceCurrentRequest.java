package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author by wangqiang
 * @date 2023/5/23.
 */
@Data
public class DeviceCurrentRequest implements Serializable {

    private String deviceId;

    private String currentConnectCount;
}
