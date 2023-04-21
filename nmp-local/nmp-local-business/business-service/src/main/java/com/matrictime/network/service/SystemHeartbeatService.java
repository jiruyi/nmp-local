package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.SystemHeartbeatRequest;
import com.matrictime.network.response.SystemHeartbeatResponse;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
public interface SystemHeartbeatService {

    Result<Integer> updateSystemHeartbeat(SystemHeartbeatRequest systemHeartbeatRequest);

    Result<SystemHeartbeatResponse> selectSystemHeartbeat(SystemHeartbeatRequest systemHeartbeatRequest);
}
