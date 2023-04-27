package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class QueryPhysicalDevicesReq implements Serializable {

    private static final long serialVersionUID = 6218396220016130527L;

    /**
     * 关联小区id
     */
    private String relationOperatorId;
}
