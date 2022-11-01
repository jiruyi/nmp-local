package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuerySystemReq implements Serializable {

    private static final long serialVersionUID = -6592288038854549383L;

    /**
     * 系统类型（不传：所有系统 1：运营系统 2：运维系统 3:运控系统）
     */
    private String sysType;
}
