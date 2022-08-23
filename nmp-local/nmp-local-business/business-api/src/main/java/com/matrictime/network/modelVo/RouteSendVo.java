package com.matrictime.network.modelVo;

import lombok.Data;

@Data
public class RouteSendVo {
    private String id;

    private String accessDeviceId;

    private String boundaryDeviceId;

    private String createUser;

    private String createTime;

    private String updateTime;

    private String updateUser;

    private Byte isExist;
}
