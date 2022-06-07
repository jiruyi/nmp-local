package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.ConfigurationDomainService;
import com.matrictime.network.dao.mapper.NmplConfigurationMapper;
import com.matrictime.network.dao.model.NmplConfiguration;
import com.matrictime.network.dao.model.NmplConfigurationExample;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.request.ConfigurationReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Service
@Slf4j
public class ConfigurationDomainServiceImpl implements ConfigurationDomainService {
    @Autowired
    NmplConfigurationMapper nmplConfigurationMapper;

    @Override
    public Integer insertOrUpdate(ConfigurationReq configurationReq) {
        if(configurationReq.getDeviceId()==null||configurationReq.getType()==null){
            throw new SystemException("参数缺失");
        }
        NmplConfigurationExample nmplConfigurationExample = new NmplConfigurationExample();
        nmplConfigurationExample.createCriteria().andDeviceIdEqualTo(configurationReq.getDeviceId()).andTypeEqualTo(configurationReq.getType());
        List<NmplConfiguration> list = nmplConfigurationMapper.selectByExample(nmplConfigurationExample);
        NmplConfiguration configuration = new NmplConfiguration();
        BeanUtils.copyProperties(configurationReq,configuration);
        Integer res = 0;
        if(!CollectionUtils.isEmpty(list)){
            res = nmplConfigurationMapper.updateByPrimaryKeySelective(configuration);
        }else {
            res  =nmplConfigurationMapper.insertSelective(configuration);
        }
        return res;
    }

    @Override
    public List<NmplConfiguration> queryByCondition(ConfigurationReq configurationReq) {
        NmplConfigurationExample nmplConfigurationExample = new NmplConfigurationExample();
        NmplConfigurationExample.Criteria criteria = nmplConfigurationExample.createCriteria();

        if(configurationReq.getType()!=null){
            criteria.andTypeEqualTo(configurationReq.getType());
        }
        if(configurationReq.getRealIp()!=null){
            criteria.andRealIpEqualTo(configurationReq.getRealIp());
        }
        if(configurationReq.getRealPort()!=null){
            criteria.andRealPortEqualTo(configurationReq.getRealPort());
        }
        if(configurationReq.getDeviceId()!=null){
            criteria.andDeviceIdEqualTo(configurationReq.getDeviceId());
        }
        List<NmplConfiguration> nmplConfigurations = nmplConfigurationMapper.selectByExample(nmplConfigurationExample);
        return nmplConfigurations;
    }
}
