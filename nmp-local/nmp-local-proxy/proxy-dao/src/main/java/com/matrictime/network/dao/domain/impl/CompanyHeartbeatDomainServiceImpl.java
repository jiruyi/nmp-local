package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.CompanyHeartbeatDomainService;
import com.matrictime.network.dao.mapper.NmplCompanyHeartbeatMapper;
import com.matrictime.network.dao.model.NmplBaseStationInfoExample;
import com.matrictime.network.dao.model.NmplCompanyHeartbeat;
import com.matrictime.network.dao.model.NmplCompanyHeartbeatExample;
import com.matrictime.network.modelVo.CompanyHeartbeatVo;
import com.matrictime.network.response.CompanyHeartbeatResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/9/12.
 */
@Service
public class CompanyHeartbeatDomainServiceImpl implements CompanyHeartbeatDomainService {

    @Resource
    private NmplCompanyHeartbeatMapper companyHeartbeatMapper;


    @Override
    public CompanyHeartbeatResponse selectCompanyHeartbeat() {
        CompanyHeartbeatResponse companyHeartbeatResponse = new CompanyHeartbeatResponse();
        List<CompanyHeartbeatVo> list = new ArrayList<>();
        NmplCompanyHeartbeatExample companyHeartbeatExample = new NmplCompanyHeartbeatExample();
        List<NmplCompanyHeartbeat> nmplCompanyHeartbeats = companyHeartbeatMapper.selectByExample(companyHeartbeatExample);
        for(NmplCompanyHeartbeat nmplCompanyHeartbeat: nmplCompanyHeartbeats){
            CompanyHeartbeatVo companyHeartbeatVo = new CompanyHeartbeatVo();
            BeanUtils.copyProperties(nmplCompanyHeartbeat,companyHeartbeatVo);
            list.add(companyHeartbeatVo);
        }
        companyHeartbeatResponse.setList(list);
        return companyHeartbeatResponse;
    }
}
