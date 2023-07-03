package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class CollectBaseResp implements Serializable {

    private static final long serialVersionUID = 4305998790113447706L;

    // jsonStringResp
    private String resp;
}
