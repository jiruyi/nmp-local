package com.matrictime.network.response;

import com.matrictime.network.modelVo.NmplDeviceVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SelectDevicesForSignalResp implements Serializable {
    private static final long serialVersionUID = -897810915598299314L;

    /**
     * 设备信息
     */
    private List<NmplDeviceVo> vos;
}
