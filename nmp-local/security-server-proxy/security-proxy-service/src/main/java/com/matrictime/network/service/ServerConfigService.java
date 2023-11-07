package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.ServerConfigVo;

/**
 * @author by wangqiang
 * @date 2023/11/6.
 */
public interface ServerConfigService {

    Result<Integer> insertConfig(ServerConfigVo serverConfigVo);
}
