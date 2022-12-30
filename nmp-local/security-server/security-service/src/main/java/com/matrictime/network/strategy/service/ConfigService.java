package com.matrictime.network.strategy.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.req.ConfigReq;

public interface ConfigService {


    Result insertOrUpdate(ConfigReq configReq);

    Result query();

    void checkReq(ConfigReq configReq);

}
