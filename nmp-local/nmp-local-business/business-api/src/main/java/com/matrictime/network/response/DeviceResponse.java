package com.matrictime.network.response;

import com.matrictime.network.modelVo.DeviceInfoVo;

import java.util.List;

public class DeviceResponse extends BaseResponse{
    private List<DeviceInfoVo> deviceInfoVos;

    public List<DeviceInfoVo> getDeviceInfoVos() {
        return deviceInfoVos;
    }

    public void setDeviceInfoVos(List<DeviceInfoVo> deviceInfoVos) {
        this.deviceInfoVos = deviceInfoVos;
    }
}