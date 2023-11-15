package com.matrictime.network.req;

import lombok.Data;

@Data
public class StartServerReq {

    /**
     * 入网id
     */
    private String networkId;

    /**
     * 操作（重启：restart）
     */
    private String action;
}
