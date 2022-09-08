package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class VerifyTokenReq implements Serializable {

    private static final long serialVersionUID = 5434766417171377638L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * token
     */
    private String token;
}
