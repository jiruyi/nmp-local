package com.matrictime.network.dao.model.extend;

import lombok.Data;

@Data
public class DeviceInfo {

    private String systemId;

    /**
     * 设备类型 01:小区内基站 02:小区边界基站 11:密钥中心
     */
    private String systemType;

    /**
     * 局域网port
     */
    private String lanPort;


}
