package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;
@Data
public class NetErrResp implements Serializable {

    private static final long serialVersionUID = 7419030479285697038L;

    String ip;

    String deviceName;
}
