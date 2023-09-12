package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.response.CompanyHeartbeatResponse;

/**
 * @author by wangqiang
 * @date 2023/8/18.
 */
public interface CompanyHeartbeatService {

    Result<Integer> insertCompanyHeartbeat(CompanyHeartbeatResponse companyHeartbeatResponse);
}
