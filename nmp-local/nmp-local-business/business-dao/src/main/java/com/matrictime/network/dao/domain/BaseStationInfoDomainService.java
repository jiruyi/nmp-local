package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.CommunityBaseStationVo;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.BaseStationCountRequest;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.CurrentCountRequest;
import com.matrictime.network.response.BelongInformationResponse;
import com.matrictime.network.response.CountBaseStationResponse;
import com.matrictime.network.response.PageInfo;

import java.util.List;


public interface BaseStationInfoDomainService {

    int insertBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    int updateBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    int deleteBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    PageInfo<BaseStationInfoVo> selectBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    List<BaseStationInfoVo> selectLinkBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    List<BaseStationInfoVo> selectForRoute(BaseStationInfoRequest baseStationInfoRequest);

    List<BaseStationInfoVo> selectActiveBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest);

    List<BaseStationInfoVo> selectBaseStationBatch(List<String> list );

    StationVo selectDeviceId(BaseStationInfoRequest baseStationInfoRequest);

    BaseStationInfoVo selectByNetworkId(BaseStationInfoRequest baseStationInfoRequest);

    List<BaseStationInfoVo> selectByOperatorId(BaseStationInfoRequest baseStationInfoRequest);

    PageInfo<BaseStationInfoVo> selectBaseStationList(BaseStationInfoRequest baseStationInfoRequest);

    List<BaseStationInfoVo> selectBaseStation(BaseStationInfoRequest baseStationInfoRequest);

    void insertCheckUnique(BaseStationInfoRequest baseStationInfoRequest);

    void updateCheckUnique(BaseStationInfoRequest baseStationInfoRequest);

    void deleteCheck(BaseStationInfoRequest baseStationInfoRequest);

    BelongInformationResponse selectBelongInformation();

    CountBaseStationResponse countBaseStation(BaseStationInfoRequest baseStationInfoRequest);

    int updateConnectCount(BaseStationCountRequest baseStationCountRequest);

    List<CommunityBaseStationVo> selectPhysicalDevice(BaseStationInfoRequest baseStationInfoRequest);

    int updateCurrentConnectCount(CurrentCountRequest currentCountRequest);
}
