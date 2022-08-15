package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.request.AddBaseStationInfoRequest;
import com.matrictime.network.request.UpdateBaseStationInfoRequest;
import com.matrictime.network.request.DeleteBaseStationInfoRequest;

import java.util.List;


public interface BaseStationInfoService {

    Result<Integer> addBaseStationInfo(List<BaseStationInfoVo> infoVos);

    Result<Integer> updateBaseStationInfo(List<BaseStationInfoVo> infoVos);

    Result<Integer> deleteBaseStationInfo(DeleteBaseStationInfoRequest request);
}
