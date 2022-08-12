package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.InsertBaseStationInfoRequest;
import com.matrictime.network.request.UpdateBaseStationInfoRequest;
import com.matrictime.network.request.DeleteBaseStationInfoRequest;


public interface BaseStationInfoService {

    Result<Integer> insertBaseStationInfo(InsertBaseStationInfoRequest request);

    Result<Integer> updateBaseStationInfo(UpdateBaseStationInfoRequest request);

    Result<Integer> deleteBaseStationInfo(DeleteBaseStationInfoRequest request);
}
