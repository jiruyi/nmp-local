package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseReq implements Serializable {
    private static final long serialVersionUID = 4048666668501172907L;

    /**
     * 0：非密区，1：密区
     */
    private String destination;

    private Integer uuid;

    private String url;

    private String encryptParam;

    private String commonParam;

    private String commonKey;
}
