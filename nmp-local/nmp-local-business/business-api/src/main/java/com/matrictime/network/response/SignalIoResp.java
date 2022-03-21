package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SignalIoResp implements Serializable {
    private static final long serialVersionUID = -6545042307814424512L;

    /**
     * 成功设备id列表
     */
    List<String> successIds;

    /**
     * 失败设备id列表
     */
    List<String> failIds;
}
