package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.util.IpUtil;
import com.matrictime.network.dao.domain.DeviceDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.NmplLinkMapper;
import com.matrictime.network.dao.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private NmplLinkMapper linkMapper;

    @Autowired
    private NmplBaseStationInfoMapper stationInfoMapper;

    private static final  String STATION_ACTIVE = "02";

    private static final  String DC_BS_LINK = "04";


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

    /**
      * @title getLocalDeviceInfo
      * @param [deviceType]
      * @return com.matrictime.network.dao.model.NmplDeviceInfo
      * @description
      * @author jiruyi
      * @create 2023/9/7 0007 16:13
      */
    @Override
    public NmplDeviceInfo getLocalDeviceInfo(String deviceType) {
        NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
        deviceInfoExample.createCriteria().andDeviceTypeEqualTo(deviceType).andIsExistEqualTo(true);
        List<NmplDeviceInfo>  nmplDeviceInfos = deviceInfoMapper.selectByExample(deviceInfoExample);
        if(CollectionUtils.isEmpty(nmplDeviceInfos)){
            return null;
        }
        if(nmplDeviceInfos.size() == 1){
            return nmplDeviceInfos.get(0);
        }
        //获取ipv4 找到本机的数据采集系统
        Set<String> result  = IpUtil.getLocalIp4Address();
        for(NmplDeviceInfo nmplDeviceInfo : nmplDeviceInfos ){
            if (result.contains(nmplDeviceInfo.getLanIp())){
                return nmplDeviceInfo;
            }
        }
        return null;
    }

    /**
      * @title getDeviceInfoByLinkRelation
      * @return com.matrictime.network.dao.model.NmplDeviceInfo
      * @description
      * @author jiruyi
      * @create 2023/9/7 0007 16:35
      */
    @Override
    public NmplBaseStationInfo getStationInfoByLocalDCLink() {
        //获取本机数据采集系统信息
        NmplDeviceInfo deviceInfo = getLocalDeviceInfo(DeviceTypeEnum.DAT_COLLECT.getCode());
        if(ObjectUtils.isEmpty(deviceInfo)){
            return null;
        }
       //查询链路关系 数据采集到边界基站
        NmplLinkExample linkExample = new NmplLinkExample();
        linkExample.createCriteria().andMainDeviceIdEqualTo(deviceInfo.getDeviceId())
                .andLinkRelationEqualTo(DC_BS_LINK)
        .andIsExistEqualTo(true).andIsOnEqualTo(true);
        List<NmplLink>  linkList =  linkMapper.selectByExample(linkExample);
        if(CollectionUtils.isEmpty(linkList)){
            return null;
        }
        //获取第一条的信息
        List<String> deviceIds = linkList.stream().map(NmplLink::getFollowDeviceId).collect(Collectors.toList());
        //获取从设备的信息
        NmplBaseStationInfoExample stationInfoExample = new NmplBaseStationInfoExample();
        stationInfoExample.createCriteria().andStationIdIn(deviceIds).andIsExistEqualTo(true);
        List<NmplBaseStationInfo>  stationInfos =   stationInfoMapper.selectByExample(stationInfoExample);
        if(CollectionUtils.isEmpty(stationInfos)){
            return null;
        }
        return stationInfos.get(0);
    }
}
