package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.CompanyHeartbeatDomainService;
import com.matrictime.network.dao.mapper.extend.NmplCompanyHeartbeatExtMapper;
import com.matrictime.network.modelVo.CompanyHeartbeatVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/18.
 */
@Service
public class CompanyHeartbeatDomainServiceImpl implements CompanyHeartbeatDomainService {

    @Resource
    private NmplCompanyHeartbeatExtMapper companyHeartbeatExtMapper;

    @Override
    public int insertCompanyHeartbeat(List<CompanyHeartbeatVo> companyHeartbeatVoList) {
        int i = companyHeartbeatExtMapper.insertCompanyHeartbeat(companyHeartbeatVoList);
        return i;
    }
}
