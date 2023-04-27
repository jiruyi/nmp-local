package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class QueryPhysicalDevicesResourceReq implements Serializable {

    private static final long serialVersionUID = -5018601281648570959L;

    /**
     * 设备ip
     */
    private String deviceIp;
}
