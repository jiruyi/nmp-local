package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplCompanyHeartbeat;
import com.matrictime.network.request.SelectRequest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/18.
 */
public interface NmplCompanyHeartbeatExtMapper {

    List<NmplCompanyHeartbeat> selectCompanyHeartbeat(SelectRequest selectRequest);
}
