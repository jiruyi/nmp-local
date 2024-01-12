package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.ProxyConfigEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.domain.ProxyConfigDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.NmplLocalBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplLocalDeviceInfoMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.ProxyConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

import static com.matrictime.network.constant.DataConstants.IS_EXIST;

@Service
@Slf4j
public class ProxyConfigServiceImpl extends SystemBaseService implements ProxyConfigService {

    @Autowired
    private ProxyConfigDomainService proxyConfigDomainService;

    @Resource
    private NmplLocalBaseStationInfoMapper localBaseStationInfoMapper;

    @Resource
    private NmplLocalDeviceInfoMapper localDeviceInfoMapper;

    @Override
    @Transactional
    public Result addOrUpdateLocalIpConfig() {
        Result result = null;
        try {
            Set<String> localIps = new HashSet<>();
            NmplLocalBaseStationInfoExample baseStationInfoExample = new NmplLocalBaseStationInfoExample();
            baseStationInfoExample.createCriteria().andIsExistEqualTo(IS_EXIST);
            List<NmplLocalBaseStationInfo> localBaseStationInfos = localBaseStationInfoMapper.selectByExample(baseStationInfoExample);
            for (NmplLocalBaseStationInfo info:localBaseStationInfos){
                localIps.add(info.getLanIp());
            }

            NmplLocalDeviceInfoExample deviceInfoExample = new NmplLocalDeviceInfoExample();
            deviceInfoExample.createCriteria().andIsExistEqualTo(IS_EXIST);
            List<NmplLocalDeviceInfo> localDeviceInfos = localDeviceInfoMapper.selectByExample(deviceInfoExample);
            for (NmplLocalDeviceInfo info:localDeviceInfos){
                localIps.add(info.getLanIp());
            }

            String newConfig = JSONObject.toJSONString(localIps);
            NmplProxyConfig proxyConfig = proxyConfigDomainService.queryProxyConfigByCode(ProxyConfigEnum.LOCAL_IP.getCode());
            if (proxyConfig != null && newConfig.equals(proxyConfig.getConfigValue())){
                return buildResult(null);
            }

            NmplProxyConfig nmplProxyConfig = new NmplProxyConfig();
            nmplProxyConfig.setConfigCode(ProxyConfigEnum.LOCAL_IP.getCode());
            nmplProxyConfig.setConfigDesc(ProxyConfigEnum.LOCAL_IP.getDesc());
            nmplProxyConfig.setConfigValue(newConfig);
            int dataResult = proxyConfigDomainService.addOrUpdateProxyConfig(nmplProxyConfig);
            result = buildResult(dataResult);

        }catch (Exception e){
            log.error("ProxyConfigServiceImpl.addOrUpdateLocalIpConfig：{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }
}
