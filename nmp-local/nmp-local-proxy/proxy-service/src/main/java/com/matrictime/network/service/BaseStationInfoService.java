package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.CenterBaseStationInfoVo;
import com.matrictime.network.request.AddBaseStationInfoRequest;
import com.matrictime.network.request.DeleteBaseStationInfoRequest;

import java.util.List;


public interface BaseStationInfoService {

    Result<Integer> addBaseStationInfo(AddBaseStationInfoRequest infoVo);

    Result<Integer> updateBaseStationInfo(BaseStationInfoVo infoVo);

    Result<Integer> deleteBaseStationInfo(DeleteBaseStationInfoRequest request);

    void initLocalInfo(List<CenterBaseStationInfoVo> infoVo);

    void initInfo(List<CenterBaseStationInfoVo> baseStationInfoList);
}
