package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.LinkRelationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.request.LinkRelationRequest;
import com.matrictime.network.response.PageInfo;

import java.util.List;

public interface LinkRelationDomainService {
    int insertLinkRelation(LinkRelationRequest linkRelationRequest);

    int deleteLinkRelation(LinkRelationRequest linkRelationRequest);

    int updateLinkRelation(LinkRelationRequest linkRelationRequest);

    PageInfo<LinkRelationVo> selectLinkRelation(LinkRelationRequest linkRelationRequest);

    List<BaseStationInfoVo> selectLinkRelationStation(BaseStationInfoRequest baseStationInfoRequest);

    List<DeviceInfoVo> selectLinkRelationDevice(DeviceInfoRequest deviceInfoRequest);
}
