package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.ConfigurationValueRequest;
import com.matrictime.network.resp.ParameterConfigurationResp;

/**
 * @author by wangqiang
 * @date 2023/10/27.
 */
public interface ConfigurationService {

    Result<Integer> insertConfiguration(ConfigurationValueRequest configurationValueRequest);

    Result<ParameterConfigurationResp> selectConfiguration(ConfigurationValueRequest configurationValueRequest);

}
