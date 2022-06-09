package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplConfiguration;
import com.matrictime.network.request.ConfigurationReq;

import java.util.List;

public interface ConfigurationDomainService {

    public Integer insertOrUpdate(ConfigurationReq configurationReq);


    public List<NmplConfiguration> queryByCondition(ConfigurationReq configurationReq);
}
