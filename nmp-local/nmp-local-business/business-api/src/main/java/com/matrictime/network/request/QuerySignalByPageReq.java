package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuerySignalByPageReq extends BaseRequest implements Serializable {
    private static final long serialVersionUID = 1920174577866638073L;

    /**
     * 用户id
     */
    private String userId;
}
