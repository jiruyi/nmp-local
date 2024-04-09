package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.ConfigurationReq;
import com.matrictime.network.request.EditDataCollectConfigReq;

public interface ConfigurationService {
    Result insertOrUpdate(ConfigurationReq configurationReq);

    Result query(ConfigurationReq configurationReq);

    Result reportBusinessData(EditDataCollectConfigReq req);


    Result reportDeviceBusinessData(EditDataCollectConfigReq req);
}
