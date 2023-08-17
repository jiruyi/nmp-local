package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryDeviceReq implements Serializable {

    private static final long serialVersionUID = 4203872920698447898L;

    /**
     * 小区入网码
     */
    private String companyNetworkId;
}
