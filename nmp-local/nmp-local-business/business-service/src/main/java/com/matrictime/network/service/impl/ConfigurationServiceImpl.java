package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.domain.ConfigurationDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplCompanyInfoVo;
import com.matrictime.network.request.ConfigurationReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.ConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConfigurationServiceImpl extends SystemBaseService implements ConfigurationService {

    @Autowired
    ConfigurationDomainService configurationDomainService;

    @Override
    public Result insertOrUpdate(ConfigurationReq configurationReq) {
        Result result = null;
        try {
            result = buildResult(configurationDomainService.insertOrUpdate(configurationReq));
        }catch (Exception e){
            log.error("设备配置信息更新异常：",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result query(ConfigurationReq configurationReq) {
        Result result = null;
        try {
            result = buildResult(configurationDomainService.queryByCondition(configurationReq));
        }catch (Exception e){
            log.error("设备配置信息查询异常：",e.getMessage());
            result = failResult(e);
        }
        return result;
    }
}
