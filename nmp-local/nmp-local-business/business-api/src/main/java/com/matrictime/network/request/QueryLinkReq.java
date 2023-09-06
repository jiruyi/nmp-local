package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryLinkReq extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 6531173522863837819L;

    /**
     * 链路名称
     */
    private String linkName;

    /**
     * 链路关系: 01:边界基站-边界基站,02:接入基站-密钥中心,03:边界基站-密钥中心,04:采集系统-边界基站
     */
    private String linkRelation;
}
