package com.matrictime.network.api.response;

import com.matrictime.network.api.request.RegisterReq;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterResp implements Serializable {
    private static final long serialVersionUID = 3009175250152732950L;

    /**
     * 用户id
     */
    private String userId;

}
