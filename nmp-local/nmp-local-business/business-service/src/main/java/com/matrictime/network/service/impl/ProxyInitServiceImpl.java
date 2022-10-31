package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.enums.StationTypeEnum;
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
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * zyj
 * @author keriezhang
 * @date 2022/9/19
 */
@Service
@Slf4j
public class ProxyInitServiceImpl extends SystemBaseService implements ProxyInitService {

    @Resource
    NmplBaseStationMapper nmplBaseStationMapper;

    @Resource
    NmplDeviceMapper nmplDeviceMapper;

    @Resource
    NmplRouteMapper nmplRouteMapper;

    @Resource
    NmplLinkRelationMapper nmplLinkRelationMapper;

    @Resource
    NmplOutlinePcInfoMapper nmplOutlinePcInfoMapper;

    @Resource
    NmplBusinessRouteMapper nmplBusinessRouteMapper;

    @Resource
    NmplStaticRouteMapper nmplStaticRouteMapper;

    @Resource
    NmplInternetRouteMapper nmplInternetRouteMapper;

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
            getRouteVoList(proxyResp);
            getlinkRelationVoList(deviceIds,proxyResp);
            getOutlinePcInfoList(proxyResp);
            result = buildResult(proxyResp);
        }catch (Exception e){
            log.error("代理初始化异常异常：",e.getMessage());
            result = failResult(e);
        }
        return result;
    }


    /**
     * 参数校验
     * @param req
     */
    private void checkParam(ProxyReq req){
        if(req.getIp().isEmpty()){
            throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }



    /**
     * 获取该ip下的基站信息
     * @param ip
     * @return
     */
    private ProxyBaseStationInfoVo getLocalStationInfoVo(String ip){
        NmplBaseStationExample nmplBaseStationExample = new NmplBaseStationExample();
        nmplBaseStationExample.createCriteria().andLanIpEqualTo(ip).andIsExistEqualTo(true);
        List<NmplBaseStation> nmplBaseStationInfos = nmplBaseStationMapper.selectByExampleWithBLOBs(nmplBaseStationExample);
        if(!CollectionUtils.isEmpty(nmplBaseStationInfos)){
            ProxyBaseStationInfoVo infoVo = new ProxyBaseStationInfoVo();
            BeanUtils.copyProperties(nmplBaseStationInfos.get(0),infoVo);
            log.info("localStationInfo get sucess");
            return infoVo;
        }else {
            return null;
        }
    }

    /**
     * 获取该ip下的设备信息
     * @param ip
     * @return
     */
    private List<ProxyDeviceInfoVo> getLocalDeviceInfoVo(String ip){
        NmplDeviceExample nmplDeviceExample = new NmplDeviceExample();
        nmplDeviceExample.createCriteria().andLanIpEqualTo(ip).andIsExistEqualTo(true);
        List<NmplDevice> nmplDeviceInfos = nmplDeviceMapper.selectByExampleWithBLOBs(nmplDeviceExample);

        List<ProxyDeviceInfoVo> res = new ArrayList<>();
        for (NmplDevice nmplDeviceInfo : nmplDeviceInfos) {
            ProxyDeviceInfoVo deviceInfoVo = new ProxyDeviceInfoVo();
            BeanUtils.copyProperties(nmplDeviceInfo,deviceInfoVo);
            res.add(deviceInfoVo);
        }
        log.info("deviceInfo get success");
        return res;
    }


    /**
     * 获取该ip下存在的基站设备id
     * @param proxyResp
     * @param req
     * @return
     */
    private List<String> getLocalDeviceIds (ProxyResp proxyResp,ProxyReq req){
        ProxyBaseStationInfoVo localStation = getLocalStationInfoVo(req.getIp());
        List<ProxyDeviceInfoVo> localDeviceInfoVos = getLocalDeviceInfoVo(req.getIp());
        if(localStation==null&&CollectionUtils.isEmpty(localDeviceInfoVos)){
            proxyResp.setExist(false);
            log.info("this ip device is not exsit");
        }else {
            proxyResp.setLocalStation(localStation);
            proxyResp.setLocalDeviceInfoVos(localDeviceInfoVos);
            proxyResp.setExist(true);
        }
        List<String> deviceIds = new ArrayList<>();
        if(localStation!=null){
            deviceIds.add(localStation.getStationId());
        }
        for (ProxyDeviceInfoVo localDeviceInfoVo : localDeviceInfoVos) {
            deviceIds.add(localDeviceInfoVo.getDeviceId());
        }
        return deviceIds;
    }


    /**
     * 获取该ip下所有关联的路由关系
     * @param proxyResp
     */
    private void getRouteVoList(ProxyResp proxyResp){
        NmplBusinessRouteExample example = new NmplBusinessRouteExample();
        example.createCriteria().andNetworkIdNotEqualTo("");
        List<NmplBusinessRoute> nmplBusinessRoutes = nmplBusinessRouteMapper.selectByExampleWithBLOBs(example);

        List<NmplInternetRoute> nmplInternetRoutes = nmplInternetRouteMapper.selectByExampleWithBLOBs(null);

        List<NmplStaticRoute> nmplStaticRoutes = nmplStaticRouteMapper.selectByExampleWithBLOBs(null);
        //业务服务
        for (NmplBusinessRoute nmplBusinessRoute : nmplBusinessRoutes) {
            ProxyBusinessRouteVo proxyBusinessRouteVo = new ProxyBusinessRouteVo();
            BeanUtils.copyProperties(nmplBusinessRoute,proxyBusinessRouteVo);
//            proxyBusinessRouteVo.setByteNetworkId(ArrayUtils.toObject(nmplBusinessRoute.getByteNetworkId()));
            proxyBusinessRouteVo.setByteNetworkId(nmplBusinessRoute.getByteNetworkId());
            proxyResp.getBusinessRouteVoList().add(proxyBusinessRouteVo);
        }
        //出网路由
        for (NmplInternetRoute nmplInternetRoute : nmplInternetRoutes) {
            ProxyInternetRouteVo proxyInternetRouteVo = new ProxyInternetRouteVo();
            BeanUtils.copyProperties(nmplInternetRoute,proxyInternetRouteVo);
//            proxyInternetRouteVo.setByteNetworkId(ArrayUtils.toObject(nmplInternetRoute.getByteNetworkId()));
            proxyResp.getInternetRouteVoList().add(proxyInternetRouteVo);
        }
        //静态分配
        for (NmplStaticRoute nmplStaticRoute : nmplStaticRoutes) {
            ProxyStaticRouteVo proxyStaticRouteVo =new ProxyStaticRouteVo();
            BeanUtils.copyProperties(nmplStaticRoute,proxyStaticRouteVo);
//            proxyStaticRouteVo.setByteNetworkId(ArrayUtils.toObject(nmplStaticRoute.getByteNetworkId()));
            proxyResp.getStaticRouteVoList().add(proxyStaticRouteVo);
        }

        log.info("route query sucess");
    }

    /**
     * 获取该ip下所有关联的链路关系
     * @param deviceIds
     * @param proxyResp
     */
    private void getlinkRelationVoList(List<String>deviceIds,ProxyResp proxyResp){
        //只推送主设备
        NmplLinkRelationExample nmplLinkRelationExample = new NmplLinkRelationExample();
        nmplLinkRelationExample.createCriteria().andMainDeviceIdIn(deviceIds);
        List<NmplLinkRelation> nmplLinkRelations = nmplLinkRelationMapper.selectByExample(nmplLinkRelationExample);
        /**
         * 创建设备ID映射设备类型 hash表
         */
        Map<String,String> deviceTypeMap = new HashMap<>();
        if(null != proxyResp.getLocalStation()){
            deviceTypeMap.put(proxyResp.getLocalStation().getStationId(),StationTypeEnum.BASE.getCode());
        }
        if(!CollectionUtils.isEmpty(proxyResp.getLocalDeviceInfoVos())){
            for (ProxyDeviceInfoVo deviceInfoVo : proxyResp.getLocalDeviceInfoVos()) {
                deviceTypeMap.put(deviceInfoVo.getDeviceId(),deviceInfoVo.getDeviceType());
            }
        }
        /**
         * 获取链路的设备类型 通知代理更新对应的表
         */
        List<ProxyLinkRelationVo> linkRelationVos = new ArrayList<>();
        for (NmplLinkRelation nmplLinkRelation : nmplLinkRelations) {
            ProxyLinkRelationVo linkRelationVo = new ProxyLinkRelationVo();
            BeanUtils.copyProperties(nmplLinkRelation,linkRelationVo);
            if(deviceTypeMap.get(linkRelationVo.getMainDeviceId())!=null){
                linkRelationVo.setNoticeDeviceType(deviceTypeMap.get(linkRelationVo.getMainDeviceId()));
//                //当两种设备在同一个ip下并且有链路关系 此时多加一条数据推送
//                if(deviceTypeMap.get(linkRelationVo.getFollowDeviceId())!=null){
//                    ProxyLinkRelationVo relationVo = new ProxyLinkRelationVo();
//                    BeanUtils.copyProperties(linkRelationVo,relationVo);
//                    relationVo.setNoticeDeviceType(deviceTypeMap.get(linkRelationVo.getFollowDeviceId()));
//                    linkRelationVos.add(relationVo);
//                }
            }
//            else {
//                linkRelationVo.setNoticeDeviceType(deviceTypeMap.get(linkRelationVo.getFollowDeviceId()));
//            }
            linkRelationVos.add(linkRelationVo);
        }
        proxyResp.setLinkRelationVoList(linkRelationVos);
        log.info("link query sucess");

    }

    /**
     * 当该ip下存在设备、基站 获取所有的基站信息
     * @param proxyResp
     */
    private void getStationInfoList(ProxyResp proxyResp){
        List<ProxyBaseStationInfoVo> stationInfoVos = new ArrayList<>();
        List<NmplBaseStation> nmplBaseStationInfos = nmplBaseStationMapper.selectByExampleWithBLOBs(null);
        for (NmplBaseStation nmplBaseStation : nmplBaseStationInfos) {
            ProxyBaseStationInfoVo infoVo = new ProxyBaseStationInfoVo();
            BeanUtils.copyProperties(nmplBaseStation,infoVo);
            stationInfoVos.add(infoVo);
        }
        proxyResp.setBaseStationInfoList(stationInfoVos);
    }

    /**
     * 当该ip下存在设备、基站 获取所有的设备信息
     * @param proxyResp
     */
    private void getDeviceInfoList(ProxyResp proxyResp){
        List<ProxyDeviceInfoVo> deviceInfoVos = new ArrayList<>();
        List<NmplDevice> nmplDeviceInfos = nmplDeviceMapper.selectByExampleWithBLOBs(null);
        for (NmplDevice nmplDevice : nmplDeviceInfos) {
            ProxyDeviceInfoVo deviceInfoVo = new ProxyDeviceInfoVo();
            BeanUtils.copyProperties(nmplDevice,deviceInfoVo);
            deviceInfoVos.add(deviceInfoVo);
        }
        proxyResp.setDeviceInfoVos(deviceInfoVos);
    }

    /**
     * 获取一体机信息
     * @param proxyResp
     */
    private void getOutlinePcInfoList(ProxyResp proxyResp){
        List<NmplOutlinePcInfoVo> nmplOutlinePcInfoVos = new ArrayList<>();
        List<NmplOutlinePcInfo> nmplOutlinePcInfoList = nmplOutlinePcInfoMapper.selectByExample(null);

        for (NmplOutlinePcInfo nmplOutlinePcInfo : nmplOutlinePcInfoList) {
            NmplOutlinePcInfoVo nmplOutlinePcInfoVo = new NmplOutlinePcInfoVo();
            BeanUtils.copyProperties(nmplOutlinePcInfo,nmplOutlinePcInfoVo);
            nmplOutlinePcInfoVos.add(nmplOutlinePcInfoVo);
        }
        proxyResp.setNmplOutlinePcInfoVos(nmplOutlinePcInfoVos);
        log.info("OutlinePcInfo query sucess");
    }
}
