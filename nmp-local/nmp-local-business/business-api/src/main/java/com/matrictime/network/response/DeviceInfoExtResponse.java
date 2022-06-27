package com.matrictime.network.response;

import com.matrictime.network.modelVo.NmplDeviceInfoExtVo;
import lombok.Data;

import java.util.List;

@Data
public class DeviceInfoExtResponse extends BaseResponse{
    private List<NmplDeviceInfoExtVo> list;
}
