package com.matrictime.network.dao.domain;

import com.matrictime.network.request.SystemHeartbeatRequest;
import com.matrictime.network.response.SystemHeartbeatResponse;

/**
 * @author by wangqiang
 * @date 2023/4/21.
 */
public interface SystemHeartbeatDomainService {

    SystemHeartbeatResponse selectSystemHeartbeat();
}
