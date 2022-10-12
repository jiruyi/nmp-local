package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProxyRouteInfoVo implements Serializable {
    private static final long serialVersionUID = -7599361500006609043L;
    private Long id;

    private String accessDeviceId;

    private String boundaryDeviceId;

    private String createUser;

    private Date createTime;

    private Date updateTime;

    private Date updateUser;

    private Byte isExist;
}
