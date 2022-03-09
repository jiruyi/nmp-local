package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class CheckHeartReq implements Serializable {

    private static final long serialVersionUID = 6802707409426799508L;

    /**
     * 设备编号
     */
    private String deviceId;


    /**
     * 设备状态（1：上线 2：下线）
     */
    private String status;
}
