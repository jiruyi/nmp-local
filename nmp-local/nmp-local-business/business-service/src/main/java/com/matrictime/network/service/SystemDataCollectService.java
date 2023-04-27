package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationDataVo;
import com.matrictime.network.modelVo.BorderBaseStationDataVo;
import com.matrictime.network.modelVo.KeyCenterDataVo;
import com.matrictime.network.request.DataCollectReq;

/**
 * @author by wangqiang
 * @date 2023/4/20.
 */
public interface SystemDataCollectService {
    Result<BaseStationDataVo> selectBaseStationData(DataCollectReq dataCollectReq);

    Result<BorderBaseStationDataVo> selectBorderBaseStationData(DataCollectReq dataCollectReq);

    Result<KeyCenterDataVo> selectKeyCenterData(DataCollectReq dataCollectReq);

    Result<Integer> insertSystemData(DataCollectReq dataCollectReq);
}
