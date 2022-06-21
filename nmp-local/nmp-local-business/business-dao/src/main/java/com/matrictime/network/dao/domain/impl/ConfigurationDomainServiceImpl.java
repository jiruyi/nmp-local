package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.ConfigurationDomainService;
import com.matrictime.network.dao.mapper.NmplConfigurationMapper;
import com.matrictime.network.dao.model.NmplConfiguration;
import com.matrictime.network.dao.model.NmplConfigurationExample;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.modelVo.ConfigurationVo;
import com.matrictime.network.request.ConfigurationReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Service
@Slf4j
public class ConfigurationDomainServiceImpl implements ConfigurationDomainService {
    @Autowired
    NmplConfigurationMapper nmplConfigurationMapper;

    @Override
    @Transactional
    public Integer insertOrUpdate(ConfigurationReq configurationReq) {
        List<ConfigurationVo> configurationVoList = configurationReq.getConfigurationVoList();
        Integer res = 0;
        if(!CollectionUtils.isEmpty(configurationVoList)){
            for (ConfigurationVo configurationVo : configurationVoList) {
                if(configurationVo.getDeviceId()==null||configurationVo.getType()==null||
                configurationVo.getRealIp()==null||configurationVo.getRealPort()==null||configurationVo.getUrl()==null){
                    throw new SystemException("参数缺失");
                }
                NmplConfigurationExample nmplConfigurationExample = new NmplConfigurationExample();
                nmplConfigurationExample.createCriteria().andDeviceIdEqualTo(configurationVo.getDeviceId()).andTypeEqualTo(configurationVo.getType());
                List<NmplConfiguration> list = nmplConfigurationMapper.selectByExample(nmplConfigurationExample);
                NmplConfiguration configuration = new NmplConfiguration();
                BeanUtils.copyProperties(configurationVo,configuration);
                if(!CollectionUtils.isEmpty(list)){
                    res = nmplConfigurationMapper.updateByPrimaryKeySelective(configuration);
                }else {
                    res  =nmplConfigurationMapper.insertSelective(configuration);
                }
            }
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
        if(configurationReq.getDeviceType()!=null){
            criteria.andDeviceTypeEqualTo(configurationReq.getDeviceType());
        }
        List<NmplConfiguration> nmplConfigurations = nmplConfigurationMapper.selectByExample(nmplConfigurationExample);
        return nmplConfigurations;
    }
}
