package com.matrictime.network.request;

import com.matrictime.network.modelVo.DeviceReportBusinessVo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class DeviceReportBusinessReq implements Serializable {


    private static final long serialVersionUID = 5264718079175629423L;
    /**
     * 设备id
     */
    private String deviceId;

    private String deviceType;

    private List<DeviceReportBusinessVo> deviceReportBusinessVoList = new ArrayList<>();


}
