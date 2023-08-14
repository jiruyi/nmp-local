package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.SystemHeartbeatVo;
import com.matrictime.network.request.SystemHeartbeatRequest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/8.
 */
public interface NmplSystemHeartbeatExtMapper {

    List<SystemHeartbeatVo> selectSystemHeartbeat(SystemHeartbeatRequest systemHeartbeatRequest);
}
