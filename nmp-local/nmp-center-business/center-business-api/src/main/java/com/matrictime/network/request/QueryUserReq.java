package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryUserReq implements Serializable {

    private static final long serialVersionUID = 2914227858105521283L;

    /**
     * 小区入网码
     */
    private String companyNetworkId;
}
