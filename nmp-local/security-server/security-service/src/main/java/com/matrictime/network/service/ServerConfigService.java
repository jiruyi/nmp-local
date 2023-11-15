package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.req.ServerConfigListReq;
import com.matrictime.network.req.ServerConfigRequest;
import com.matrictime.network.resp.ServerConfigResp;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
public interface ServerConfigService {

    Result<Integer> insertServerConfig(ServerConfigRequest serverConfigRequest);

    Result<Integer> deleteServerConfig(ServerConfigRequest serverConfigRequest);

    Result<ServerConfigResp> selectServerConfig(ServerConfigRequest serverConfigRequest);

    Result<Integer> synConfig(ServerConfigRequest serverConfigRequest);

    Result<Integer> synConfigList(ServerConfigListReq listReq);

    Result<Integer> insertBatchServerConfig(ServerConfigListReq listReq);
}
