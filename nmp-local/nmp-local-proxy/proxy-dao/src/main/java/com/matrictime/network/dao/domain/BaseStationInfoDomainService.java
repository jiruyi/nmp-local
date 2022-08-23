package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplBaseStationInfo;
import com.matrictime.network.modelVo.BaseStationInfoVo;

import java.util.List;


public interface BaseStationInfoDomainService {

    int insert(NmplBaseStationInfo baseStationInfo);

    int update(NmplBaseStationInfo baseStationInfo);

    int insertBaseStationInfo(List<BaseStationInfoVo> baseStationInfoVos);

    int updateBaseStationInfo(List<BaseStationInfoVo> baseStationInfoVos);

    int deleteBaseStationInfo(List<Long> ids);
}
