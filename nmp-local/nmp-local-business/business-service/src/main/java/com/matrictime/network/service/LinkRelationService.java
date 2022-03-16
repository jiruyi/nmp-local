package com.matrictime.network.service;


import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.LinkRelationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.request.LinkRelationRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.PageInfo;


public interface LinkRelationService {
    Result<Integer> insertLinkRelation(LinkRelationRequest linkRelationRequest);

    Result<Integer> deleteLinkRelation(LinkRelationRequest linkRelationRequest);

    Result<Integer> updateLinkRelation(LinkRelationRequest linkRelationRequest);

    Result<PageInfo<LinkRelationVo>> selectLinkRelation(LinkRelationRequest linkRelationRequest);

    Result<BaseStationInfoResponse> selectLinkRelationStation(BaseStationInfoRequest baseStationInfoRequest);

    Result<DeviceResponse> selectLinkRelationDevice(DeviceInfoRequest deviceInfoRequest);
}
