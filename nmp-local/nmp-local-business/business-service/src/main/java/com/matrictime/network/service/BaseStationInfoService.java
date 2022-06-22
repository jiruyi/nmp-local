package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.PageInfo;

import java.util.List;


public interface BaseStationInfoService {

    Result<Integer> insertBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    Result<Integer> updateBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    Result<Integer> deleteBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    Result<PageInfo> selectBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    Result<BaseStationInfoResponse> selectLinkBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    Result<BaseStationInfoResponse> selectActiveBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    Result<BaseStationInfoResponse> selectBaseStationBatch(List<String> list);

    Result<StationVo> selectDeviceId(BaseStationInfoRequest baseStationInfoRequest);

    BaseStationInfoVo selectByNetworkId(BaseStationInfoRequest baseStationInfoRequest);

    Result<BaseStationInfoResponse> selectByOperatorId(BaseStationInfoRequest baseStationInfoRequest);

    Result<PageInfo> selectBaseStationList(BaseStationInfoRequest baseStationInfoRequest);
}
