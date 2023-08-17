package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryCompanyUserReq implements Serializable {

    private static final long serialVersionUID = 7900194630225527004L;

    /**
     * 小区入网码
     */
    private String companyNetworkId;
}
