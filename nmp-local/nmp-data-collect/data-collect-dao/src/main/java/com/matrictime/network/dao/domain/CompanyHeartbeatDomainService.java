package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.CompanyHeartbeatVo;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/18.
 */
public interface CompanyHeartbeatDomainService {

    List<CompanyHeartbeatVo> selectCompanyHeartbeat();

}
