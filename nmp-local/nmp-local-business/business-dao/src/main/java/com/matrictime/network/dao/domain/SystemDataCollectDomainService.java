package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.BaseStationDataVo;
import com.matrictime.network.modelVo.BorderBaseStationDataVo;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.modelVo.KeyCenterDataVo;
import com.matrictime.network.request.DataCollectReq;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/20.
 */
public interface SystemDataCollectDomainService {

    BaseStationDataVo selectBaseStationData(DataCollectReq dataCollectReq);

    BorderBaseStationDataVo selectBorderBaseStationData(DataCollectReq dataCollectReq);

    KeyCenterDataVo selectKeyCenterData(DataCollectReq dataCollectReq);

    int insertSystemData(List<DataCollectVo> dataCollectVoList);


}
