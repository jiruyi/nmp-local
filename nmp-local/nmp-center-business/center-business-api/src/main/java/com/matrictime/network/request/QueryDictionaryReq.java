package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryDictionaryReq implements Serializable {

    private static final long serialVersionUID = 6963282825498415544L;
    /**
     * 主键
     */
    private Long id;

    /**
     * id名称
     */
    private String idName;

    /**
     * id编码
     */
    private String idCode;

    /**
     * 关键字
     */
    private String keyWords;
}
