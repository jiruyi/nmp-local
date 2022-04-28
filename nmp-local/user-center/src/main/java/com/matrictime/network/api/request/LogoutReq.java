package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class LogoutReq extends BaseReq implements Serializable {
    private static final long serialVersionUID = -2938103735163424052L;

    private String userId;
}
