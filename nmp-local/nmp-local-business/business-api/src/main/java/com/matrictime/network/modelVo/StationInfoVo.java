package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class StationInfoVo implements Serializable {
    private static final long serialVersionUID = -3322903716776895578L;

    /**
     * 设备主键id
     */
    private Long id;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 公网ip
     */
    private String public_network_ip;

    /**
     * 是否被选中（true:选中 false:未选中）
     */
    private Boolean isSelected = false;
}
