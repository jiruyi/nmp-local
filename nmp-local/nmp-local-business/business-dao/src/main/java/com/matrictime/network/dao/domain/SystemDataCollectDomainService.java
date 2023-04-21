package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.BaseStationDataVo;
import com.matrictime.network.modelVo.BorderBaseStationDataVo;
import com.matrictime.network.modelVo.KeyCenterDataVo;
import com.matrictime.network.request.DataCollectReq;

/**
 * @author by wangqiang
 * @date 2023/4/20.
 */
public interface SystemDataCollectDomainService {

    BaseStationDataVo selectBaseStationData();

    BorderBaseStationDataVo selectBorderBaseStationData();

    KeyCenterDataVo selectKeyCenterData();

    int insertSystemData(DataCollectReq dataCollectReq);


}
