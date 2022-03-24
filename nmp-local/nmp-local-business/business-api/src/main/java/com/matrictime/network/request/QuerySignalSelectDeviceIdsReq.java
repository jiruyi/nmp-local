package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuerySignalSelectDeviceIdsReq implements Serializable {

    private static final long serialVersionUID = -8173363457561722961L;
    /**
     * 用户id
     */
    private String userId;
}
