package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class VerifyReq implements Serializable {
    private static final long serialVersionUID = 4055666536286048869L;

    private String phoneNumber;

    private String afterSix;
}
