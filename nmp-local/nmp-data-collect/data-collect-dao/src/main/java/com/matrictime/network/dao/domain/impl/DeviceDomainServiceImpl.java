package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.util.IpUtil;
import com.matrictime.network.dao.domain.DeviceDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
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

    @Autowired
    private NmplLinkMapper linkMapper;

    @Autowired
    private NmplBusinessRouteMapper businessRouteMapper;

    @Autowired
    private NmplInternetRouteMapper internetRouteMapper;

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
    public NmplLink getDataCollectLink(String followNetId) {
        //查询链路关系 数据采集到边界基站(或者指控中心)
        NmplLinkExample linkExample = new NmplLinkExample();
        linkExample.createCriteria().andMainDeviceTypeEqualTo(DeviceTypeEnum.DAT_COLLECT.getCode())
                .andFollowNetworkIdEqualTo(followNetId)
                .andIsExistEqualTo(true).andIsOnEqualTo(true);
        List<NmplLink>  linkList =  linkMapper.selectByExample(linkExample);
        if(CollectionUtils.isEmpty(linkList)){
            return null;
        }
        return linkList.get(0);
    }

    /**
     * @title getBusinessRoute
     * @param []
     * @return com.matrictime.network.dao.model.NmplBusinessRoute
     * @description  获取被数据采集对应的指控中心入网码
     * @author jiruyi
     * @create 2023/10/9 0009 15:30
     */
    @Override
    public NmplBusinessRoute getBusinessRoute() {
        NmplBusinessRouteExample routeExample = new NmplBusinessRouteExample();
        routeExample.createCriteria().andBusinessTypeEqualTo(DeviceTypeEnum.COMMAND_CENTER.getCode())
                .andIsExistEqualTo(true);
        List<NmplBusinessRoute> routes =   businessRouteMapper.selectByExample(routeExample);
        if(CollectionUtils.isEmpty(routes)){
            return null;
        }
        return routes.get(0);
    }

    /**
     * @title getInternetRoute
     * @param [targetNetId]
     * @return com.matrictime.network.dao.model.NmplInternetRoute
     * @description 查询下一跳
     * @author jiruyi
     * @create 2023/10/10 0010 10:28
     */
    @Override
    public NmplInternetRoute getInternetRoute(String targetNetId) {
        NmplInternetRouteExample internetRouteExample = new NmplInternetRouteExample();
        internetRouteExample.createCriteria().andNetworkIdEqualTo(targetNetId).andIsExistEqualTo(true);
        List<NmplInternetRoute> routes = internetRouteMapper.selectByExample(internetRouteExample);
        if(CollectionUtils.isEmpty(routes)){
            return null;
        }
        return routes.get(0);
    }
}
