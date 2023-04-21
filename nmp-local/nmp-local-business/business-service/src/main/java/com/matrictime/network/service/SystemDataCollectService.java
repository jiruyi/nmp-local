package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationDataVo;
import com.matrictime.network.modelVo.BorderBaseStationDataVo;
import com.matrictime.network.modelVo.KeyCenterDataVo;

/**
 * @author by wangqiang
 * @date 2023/4/20.
 */
public interface SystemDataCollectService {
    Result<BaseStationDataVo> selectBaseStationData();

    Result<BorderBaseStationDataVo> selectBorderBaseStationData();

    Result<KeyCenterDataVo> selectKeyCenterData();
}
