package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.ConfigurationReq;

public interface ConfigurationService {
    Result insertOrUpdate(ConfigurationReq configurationReq);

    Result query(ConfigurationReq configurationReq);

    Result reportBusinessData();

}
