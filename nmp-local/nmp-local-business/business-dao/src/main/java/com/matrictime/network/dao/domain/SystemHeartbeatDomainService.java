package com.matrictime.network.dao.domain;

import com.matrictime.network.request.SystemHeartbeatRequest;
import com.matrictime.network.response.SystemHeartbeatResponse;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
public interface SystemHeartbeatDomainService {

    int insertSystemHeartbeat(SystemHeartbeatRequest systemHeartbeatRequest);

    int updateSystemHeartbeat(SystemHeartbeatRequest systemHeartbeatRequest);

    SystemHeartbeatResponse selectSystemHeartbeat(SystemHeartbeatRequest systemHeartbeatRequest);

}
