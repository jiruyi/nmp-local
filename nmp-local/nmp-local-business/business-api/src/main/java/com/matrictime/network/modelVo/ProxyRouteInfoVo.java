package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;

@Data
public class ProxyRouteInfoVo {
    private Long id;

    private String accessDeviceId;

    private String boundaryDeviceId;

    private String createUser;

    private Date createTime;

    private Date updateTime;

    private Date updateUser;

    private Byte isExist;
}
