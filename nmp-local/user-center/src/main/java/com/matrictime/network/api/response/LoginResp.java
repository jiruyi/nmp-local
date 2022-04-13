package com.matrictime.network.api.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginResp implements Serializable {
    private static final long serialVersionUID = 4752492437076704960L;

    private String userInfo;
}
