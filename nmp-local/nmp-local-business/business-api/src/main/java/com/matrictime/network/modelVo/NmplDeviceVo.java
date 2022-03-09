package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class NmplDeviceVo implements Serializable {

    private static final long serialVersionUID = -1368300335008603350L;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备大类（1：基站 2：其他机）
     */
    private String deviceBigType;
}
