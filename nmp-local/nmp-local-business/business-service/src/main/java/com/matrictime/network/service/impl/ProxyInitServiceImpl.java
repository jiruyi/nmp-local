package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.request.ProxyReq;
import com.matrictime.network.request.RouteRequest;
import com.matrictime.network.response.ProxyResp;
import com.matrictime.network.service.ProxyInitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ProxyInitServiceImpl extends SystemBaseService implements ProxyInitService {

    @Resource
    NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Resource
    NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Resource
    NmplRouteMapper nmplRouteMapper;

    @Resource
    NmplLinkRelationMapper nmplLinkRelationMapper;

    @Resource
    NmplOutlinePcInfoMapper nmplOutlinePcInfoMapper;


    /**
     *  代理程序启动初始化获取该ip下的所有信息
     */
    @Override
    public Result init(ProxyReq req) {
        Result result = null;
        try {
            checkParam(req);
            ProxyResp proxyResp = new ProxyResp();
            List<String> deviceIds = getLocalDeviceIds(proxyResp,req);
            if(!proxyResp.isExist()){
                return buildResult(proxyResp);
            }
            getStationInfoList(proxyResp);
            getDeviceInfoList(proxyResp);
            getRoteVoList(proxyResp);
            getlinkRelationVoList(deviceIds,proxyResp);
            getOutlinePcInfoList(proxyResp);
            result = buildResult(proxyResp);
        }catch (Exception e){
            log.error("代理初始化异常异常：",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    //参数校验
    private void checkParam(ProxyReq req){
        if(req.getIp().isEmpty()){
            throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    //获取该ip下的基站信息
    private BaseStationInfoVo getLocalStationInfoVo(String ip){
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        nmplBaseStationInfoExample.createCriteria().andLanIpEqualTo(ip);
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        if(!CollectionUtils.isEmpty(nmplBaseStationInfos)){
            BaseStationInfoVo infoVo = new BaseStationInfoVo();
            BeanUtils.copyProperties(nmplBaseStationInfos.get(0),infoVo);
            log.info("localStationInfo get sucess");
            return infoVo;
        }else {
            return null;
        }
    }

    //获取该ip下的设备信息
    private List<DeviceInfoVo> getLocalDeviceInfoVo(String ip){
        NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
        nmplDeviceInfoExample.createCriteria().andLanIpEqualTo(ip);
        List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);

        List<DeviceInfoVo> res = new ArrayList<>();
        for (NmplDeviceInfo nmplDeviceInfo : nmplDeviceInfos) {
            DeviceInfoVo deviceInfoVo = new DeviceInfoVo();
            BeanUtils.copyProperties(nmplDeviceInfo,deviceInfoVo);
            res.add(deviceInfoVo);
        }
        log.info("deviceInfo get success");
        return res;
    }

    //获取该ip下存在的基站设备id
    private List<String> getLocalDeviceIds (ProxyResp proxyResp,ProxyReq req){
        BaseStationInfoVo localStation = getLocalStationInfoVo(req.getIp());
        List<DeviceInfoVo> localDeviceInfoVos = getLocalDeviceInfoVo(req.getIp());
        if(localStation==null&&CollectionUtils.isEmpty(localDeviceInfoVos)){
            proxyResp.setExist(false);
        }else {
            proxyResp.setLocalStation(localStation);
            proxyResp.setLocalDeviceInfoVos(localDeviceInfoVos);
            proxyResp.setExist(true);
        }
        List<String> deviceIds = new ArrayList<>();
        if(localStation!=null){
            deviceIds.add(localStation.getStationId());
        }
        for (DeviceInfoVo localDeviceInfoVo : localDeviceInfoVos) {
            deviceIds.add(localDeviceInfoVo.getDeviceId());
        }
        return deviceIds;
    }

    //获取该ip下所有关联的路由关系
    private void getRoteVoList(ProxyResp proxyResp){
        if(proxyResp.getLocalStation()==null){
            return;
        }
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setAccessDeviceId(proxyResp.getLocalStation().getStationId());
        routeRequest.setBoundaryDeviceId(proxyResp.getLocalStation().getStationId());
        List<RouteVo> roteVoList = nmplRouteMapper.selectByRelationId(routeRequest);
        proxyResp.setRoteVoList(roteVoList);
    }

    //获取该ip下所有关联的链路关系
    private void getlinkRelationVoList(List<String>deviceIds,ProxyResp proxyResp){
        NmplLinkRelationExample nmplLinkRelationExample = new NmplLinkRelationExample();
        nmplLinkRelationExample.createCriteria().andMainDeviceIdIn(deviceIds);
        nmplLinkRelationExample.or().andFollowDeviceIdIn(deviceIds);
        List<NmplLinkRelation> nmplLinkRelations = nmplLinkRelationMapper.selectByExample(nmplLinkRelationExample);

        List<LinkRelationVo> linkRelationVos = new ArrayList<>();
        for (NmplLinkRelation nmplLinkRelation : nmplLinkRelations) {
            LinkRelationVo linkRelationVo = new LinkRelationVo();
            BeanUtils.copyProperties(nmplLinkRelation,linkRelationVo);
            linkRelationVos.add(linkRelationVo);
        }
        proxyResp.setLinkRelationVoList(linkRelationVos);

    }

    //当该ip下存在设备、基站 获取所有的基站信息
    private void getStationInfoList(ProxyResp proxyResp){
        List<BaseStationInfoVo> stationInfoVos = new ArrayList<>();
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(null);
        for (NmplBaseStationInfo nmplBaseStationInfo : nmplBaseStationInfos) {
            BaseStationInfoVo infoVo = new BaseStationInfoVo();
            BeanUtils.copyProperties(nmplBaseStationInfo,infoVo);
            stationInfoVos.add(infoVo);
        }
        proxyResp.setBaseStationInfoList(stationInfoVos);
    }

    //当该ip下存在设备、基站 获取所有的设备信息
    private void getDeviceInfoList(ProxyResp proxyResp){
        List<DeviceInfoVo> deviceInfoVos = new ArrayList<>();
        List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(null);
        for (NmplDeviceInfo nmplDeviceInfo : nmplDeviceInfos) {
            DeviceInfoVo deviceInfoVo = new DeviceInfoVo();
            BeanUtils.copyProperties(nmplDeviceInfo,deviceInfoVo);
            deviceInfoVos.add(deviceInfoVo);
        }
        proxyResp.setDeviceInfoVos(deviceInfoVos);
    }

    private void getOutlinePcInfoList(ProxyResp proxyResp){
        List<NmplOutlinePcInfoVo> nmplOutlinePcInfoVos = new ArrayList<>();
        List<NmplOutlinePcInfo> nmplOutlinePcInfoList = nmplOutlinePcInfoMapper.selectByExample(null);

        for (NmplOutlinePcInfo nmplOutlinePcInfo : nmplOutlinePcInfoList) {
            NmplOutlinePcInfoVo nmplOutlinePcInfoVo = new NmplOutlinePcInfoVo();
            BeanUtils.copyProperties(nmplOutlinePcInfo,nmplOutlinePcInfoVo);
            nmplOutlinePcInfoVos.add(nmplOutlinePcInfoVo);
        }
        proxyResp.setNmplOutlinePcInfoVos(nmplOutlinePcInfoVos);
    }
}
