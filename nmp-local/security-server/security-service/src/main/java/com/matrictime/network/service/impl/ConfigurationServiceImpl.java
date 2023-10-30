package com.matrictime.network.service.impl;

import com.matrictime.network.dao.mapper.NmpsConfigurationValueMapper;
import com.matrictime.network.dao.mapper.extend.ConfigurationValueExtMapper;
import com.matrictime.network.dao.model.NmpsConfigurationValue;
import com.matrictime.network.dao.model.NmpsConfigurationValueExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.ParameterConfigurationVo;
import com.matrictime.network.request.ConfigurationValueRequest;
import com.matrictime.network.resp.ParameterConfigurationResp;
import com.matrictime.network.service.ConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/10/27.
 */
@Slf4j
@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    @Resource
    private ConfigurationValueExtMapper configurationValueExtMapper;

    @Resource
    private NmpsConfigurationValueMapper configurationValueMapper;


    @Override
    public Result<Integer> insertConfiguration(ConfigurationValueRequest configurationValueRequest) {
        Result<Integer> result = new Result<>();
        try {
            int i = 0;
            NmpsConfigurationValue configurationValue = new NmpsConfigurationValue();
            BeanUtils.copyProperties(configurationValueRequest,configurationValue);
            NmpsConfigurationValueExample configurationValueExample = new NmpsConfigurationValueExample();
            NmpsConfigurationValueExample.Criteria criteria = configurationValueExample.createCriteria();
            criteria.andNetworkIdEqualTo(configurationValueRequest.getNetworkId());
            criteria.andConfigurationCodeEqualTo(configurationValueRequest.getConfigurationCode());
            List<NmpsConfigurationValue> nmpsConfigurationValues = configurationValueMapper.selectByExample(configurationValueExample);
            if(CollectionUtils.isEmpty(nmpsConfigurationValues)){
                i = configurationValueMapper.insertSelective(configurationValue);
            }else {
                i = configurationValueMapper.updateByExampleSelective(configurationValue,configurationValueExample);
            }
            if(i == 1){
                result.setSuccess(true);
                result.setResultObj(i);
            }
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("insertConfiguration:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<ParameterConfigurationResp> selectConfiguration(ConfigurationValueRequest configurationValueRequest) {
        Result<ParameterConfigurationResp> result = new Result<>();
        try {
            List<ParameterConfigurationVo> parameterConfigurationVos = configurationValueExtMapper.selectConfiguration(configurationValueRequest);
            ParameterConfigurationResp parameterConfigurationResp = new ParameterConfigurationResp();
            parameterConfigurationResp.setList(parameterConfigurationVos);
            result.setResultObj(parameterConfigurationResp);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            log.error("selectConfiguration:{}",e.getMessage());
        }
        return result;
    }
}
