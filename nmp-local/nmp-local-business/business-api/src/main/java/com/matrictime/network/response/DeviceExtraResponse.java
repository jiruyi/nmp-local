package com.matrictime.network.response;

import com.matrictime.network.modelVo.DeviceExtraVo;

import java.util.List;

public class DeviceExtraResponse extends BaseResponse{
    private List<DeviceExtraVo> list;

    public List<DeviceExtraVo> getList() {
        return list;
    }

    public void setList(List<DeviceExtraVo> list) {
        this.list = list;
    }
}
