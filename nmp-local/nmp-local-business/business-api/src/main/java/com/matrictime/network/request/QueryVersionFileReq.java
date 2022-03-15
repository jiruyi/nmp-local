package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class QueryVersionFileReq implements Serializable {

    private static final long serialVersionUID = -4831024477262023728L;

    /**
     * 系统id
     */
    private String systemId;
}
