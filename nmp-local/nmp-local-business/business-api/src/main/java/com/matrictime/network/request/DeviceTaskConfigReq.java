package com.matrictime.network.request;

import com.matrictime.network.modelVo.NmplDeviceTaskConfigVo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class DeviceTaskConfigReq implements Serializable {

    private static final long serialVersionUID = 14901239254524748L;
    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备类型 01:接入基站 02:边界基站 11:密钥中心 12:生成机 13:缓存机 20:采集设备 21:指控中心
     */
    private String deviceType;

    private List<NmplDeviceTaskConfigVo> nmplDeviceTaskConfigVoList = new ArrayList<>();

}
