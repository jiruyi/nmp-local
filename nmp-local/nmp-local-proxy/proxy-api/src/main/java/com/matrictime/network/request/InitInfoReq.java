package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;


@Data
public class InitInfoReq implements Serializable {

    private String localIp;
}
