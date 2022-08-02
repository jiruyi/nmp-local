package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class PushTokenReq extends BaseReq implements Serializable {

    private static final long serialVersionUID = -2090294898246878609L;

    private String system;

    private String type;

    private String url;

    private String body;

}
