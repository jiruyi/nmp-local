package com.matrictime.network.response;

import com.matrictime.network.modelVo.BaseStationInfoVo;

import java.util.List;

public class BaseStationInfoResponse extends BaseResponse{
    private List<BaseStationInfoVo> baseStationInfoList;

    public List<BaseStationInfoVo> getBaseStationInfoList() {
        return baseStationInfoList;
    }

    public void setBaseStationInfoList(List<BaseStationInfoVo> baseStationInfoList) {
        this.baseStationInfoList = baseStationInfoList;
    }
}