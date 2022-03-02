package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.response.BaseStationInfoResponse;


public interface BaseStationInfoService {

    Result<Integer> insertBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    Result<Integer> updateBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    Result<Integer> deleteBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    Result<BaseStationInfoResponse> selectBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);
}
