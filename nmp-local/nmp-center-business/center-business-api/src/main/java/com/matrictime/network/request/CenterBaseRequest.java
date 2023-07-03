package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class CenterBaseRequest implements Serializable {

    private static final long serialVersionUID = 4305998790113447706L;

    // jsonStringParam
    private String param;
}
