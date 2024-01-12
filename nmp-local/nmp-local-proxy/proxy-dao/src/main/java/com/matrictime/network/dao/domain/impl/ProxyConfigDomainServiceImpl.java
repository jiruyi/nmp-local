package com.matrictime.network.dao.domain.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.enums.ProxyConfigEnum;
import com.matrictime.network.dao.domain.ProxyConfigDomainService;
import com.matrictime.network.dao.mapper.NmplProxyConfigMapper;
import com.matrictime.network.dao.model.NmplProxyConfig;
import com.matrictime.network.dao.model.NmplProxyConfigExample;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.matrictime.network.constant.DataConstants.ZERO;

@Service
public class ProxyConfigDomainServiceImpl implements ProxyConfigDomainService {

    @Resource
    private NmplProxyConfigMapper proxyConfigMapper;

    @Override
    public int addOrUpdateProxyConfig(NmplProxyConfig config) {
        int result = 0;
        NmplProxyConfigExample example = new NmplProxyConfigExample();
        example.createCriteria().andConfigCodeEqualTo(config.getConfigCode());
        List<NmplProxyConfig> nmplProxyConfigs = proxyConfigMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(nmplProxyConfigs)){
            result = proxyConfigMapper.insert(config);
        }else {
            result = proxyConfigMapper.updateByExampleSelective(config, example);
        }
        return result;
    }

    @Override
    public List<String> getLocalIps() {
        List<String> result = null;
        NmplProxyConfigExample example = new NmplProxyConfigExample();
        example.createCriteria().andConfigCodeEqualTo(ProxyConfigEnum.LOCAL_IP.getCode());
        List<NmplProxyConfig> nmplProxyConfigs = proxyConfigMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(nmplProxyConfigs)){
            String configValue = nmplProxyConfigs.get(ZERO).getConfigValue();
            result = JSONObject.parseArray(configValue, String.class);
        }
        return result;
    }

    @Override
    public NmplProxyConfig queryProxyConfigByCode(String code) {
        NmplProxyConfig result = null;
        NmplProxyConfigExample example = new NmplProxyConfigExample();
        example.createCriteria().andConfigCodeEqualTo(ProxyConfigEnum.LOCAL_IP.getCode());
        List<NmplProxyConfig> nmplProxyConfigs = proxyConfigMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(nmplProxyConfigs)){
            result = nmplProxyConfigs.get(ZERO);
        }
        return result;
    }
}
