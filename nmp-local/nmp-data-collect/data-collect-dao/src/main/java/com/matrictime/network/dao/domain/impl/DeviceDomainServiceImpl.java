package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.util.IpUtil;
import com.matrictime.network.dao.domain.DeviceDomainService;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.model.NmplDeviceInfo;
import com.matrictime.network.dao.model.NmplDeviceInfoExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/28 0028 13:38
 * @desc
 */
@Slf4j
@Service
public class DeviceDomainServiceImpl implements DeviceDomainService {

    @Autowired
    private NmplDeviceInfoMapper deviceInfoMapper;


    /**
      * @title getNetworkIdByType
      * @param [deviceType]
      * @return java.lang.String
      * @description  根据设备类型 获取本机的设备入网码
      * @author jiruyi
      * @create 2023/8/28 0028 14:15
      */
    @Override
    public String getNetworkIdByType(String deviceType) {
        NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
        deviceInfoExample.createCriteria().andDeviceTypeEqualTo(deviceType).andIsExistEqualTo(true);
        List<NmplDeviceInfo>  nmplDeviceInfos = deviceInfoMapper.selectByExample(deviceInfoExample);
        if(CollectionUtils.isEmpty(nmplDeviceInfos)){
            return null;
        }
        if(nmplDeviceInfos.size() == 1){
            return nmplDeviceInfos.get(0).getStationNetworkId();
        }
        //获取ipv4 找到本机的数据采集系统
        Set<String> result  = IpUtil.getLocalIp4Address();
        for(NmplDeviceInfo nmplDeviceInfo : nmplDeviceInfos ){
            if (result.contains(nmplDeviceInfo.getLanIp())){
                return nmplDeviceInfo.getStationNetworkId();
            }
        }
        return null;
    }
}
