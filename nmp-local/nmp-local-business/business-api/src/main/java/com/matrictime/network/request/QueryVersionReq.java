package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryVersionReq implements Serializable {
    private static final long serialVersionUID = 3972118133908196774L;

    /**
     * 系统id
     */
    private String systemId;
}
