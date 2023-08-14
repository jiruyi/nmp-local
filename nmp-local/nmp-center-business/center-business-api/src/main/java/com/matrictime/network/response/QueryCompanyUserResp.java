package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryCompanyUserResp implements Serializable {

    private static final long serialVersionUID = 8835379203573390163L;

    private List companyInfo;

    /**
     * 在线用户数
     */
    private List onlineUser;

    /**
     * 接入用户数
     */
    private List accessUser;
}
