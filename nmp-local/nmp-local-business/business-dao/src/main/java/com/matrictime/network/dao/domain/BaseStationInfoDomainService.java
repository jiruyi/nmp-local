package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.request.BaseStationInfoRequest;

import java.util.List;

public interface BaseStationInfoDomainService {

    int insertBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    int updateBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    int deleteBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    List<BaseStationInfoVo> selectBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);
}
