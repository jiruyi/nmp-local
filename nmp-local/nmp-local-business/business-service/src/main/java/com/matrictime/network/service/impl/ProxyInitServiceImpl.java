package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.LinkEnum;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.request.ProxyReq;
import com.matrictime.network.response.ProxyResp;
import com.matrictime.network.service.ProxyInitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    NmplLinkMapper nmplLinkMapper;

    @Resource
    NmplOutlinePcInfoMapper nmplOutlinePcInfoMapper;

    @Resource
    NmplBusinessRouteMapper nmplBusinessRouteMapper;

    @Resource
    NmplStaticRouteMapper nmplStaticRouteMapper;

    @Resource
    NmplInternetRouteMapper nmplInternetRouteMapper;

    @Resource
    NmplDeviceTaskConfigMapper nmplDeviceTaskConfigMapper;

    @Resource
    NmplTaskConfigMapper nmplTaskConfigMapper;

    @Resource
    NmplConfigMapper nmplConfigMapper;

    @Resource
    NmplDeviceConfigMapper nmplDeviceConfigMapper;


    /**
     *  代理程序启动初始化获取该ip下的所有信息
     */
    @Override
    public Result<ProxyResp> init(ProxyReq req) {
        Result<ProxyResp> result = null;
        try {
            checkParam(req);
            ProxyResp proxyResp = new ProxyResp();
            getLocalDevices(proxyResp,req);
            if(!proxyResp.isExist()){
                return buildResult(proxyResp);
            }
            getStationInfoList(proxyResp);
            getDeviceInfoList(proxyResp);
            getRouteVoList(proxyResp);
            getlinkVoList(proxyResp);
            getOutlinePcInfoList(proxyResp);
            getDeviceTask(proxyResp);
            getDeviceConfig(proxyResp);
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
        if(CollectionUtils.isEmpty(req.getIps())){
            throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }



    /**
     * 获取该ip下的基站信息
     * @param ips
     * @return
     */
    private List<ProxyBaseStationInfoVo> getLocalStationInfoVo(List ips){
        NmplBaseStationExample nmplBaseStationExample = new NmplBaseStationExample();
        nmplBaseStationExample.createCriteria().andLanIpIn(ips).andIsExistEqualTo(true);
        List<NmplBaseStation> nmplBaseStationInfos = nmplBaseStationMapper.selectByExampleWithBLOBs(nmplBaseStationExample);
        List<ProxyBaseStationInfoVo> proxyBaseStationInfoVoList = new ArrayList<>();

        for (NmplBaseStation nmplBaseStationInfo : nmplBaseStationInfos) {
            ProxyBaseStationInfoVo infoVo = new ProxyBaseStationInfoVo();
            BeanUtils.copyProperties(nmplBaseStationInfo,infoVo);
            proxyBaseStationInfoVoList.add(infoVo);
        }
        return proxyBaseStationInfoVoList;
    }

    /**
     * 获取该ip下的设备信息
     * @param ips
     * @return
     */
    private List<ProxyDeviceInfoVo> getLocalDeviceInfoVo(List ips){
        NmplDeviceExample nmplDeviceExample = new NmplDeviceExample();
        nmplDeviceExample.createCriteria().andLanIpIn(ips).andIsExistEqualTo(true);
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
    private void getLocalDevices (ProxyResp proxyResp,ProxyReq req){
        List<String> ips = new ArrayList<>(req.getIps());
        List<ProxyBaseStationInfoVo> localStation = getLocalStationInfoVo(ips);
        List<ProxyDeviceInfoVo> localDeviceInfoVos = getLocalDeviceInfoVo(ips);
        if(CollectionUtils.isEmpty(localStation) && CollectionUtils.isEmpty(localDeviceInfoVos)){
            proxyResp.setExist(false);
            log.info("this ip device is not exsit");
        }else {
            proxyResp.setLocalStation(localStation);
            proxyResp.setLocalDeviceInfoVos(localDeviceInfoVos);
            proxyResp.setExist(true);
            Set<String> localIps = new HashSet<>();
            for (ProxyBaseStationInfoVo vo:localStation){
                localIps.add(vo.getLanIp());
            }
            for (ProxyDeviceInfoVo vo:localDeviceInfoVos){
                localIps.add(vo.getLanIp());
            }
            proxyResp.setLocalIps(new ArrayList<>(localIps));
        }
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

        List<NmplStaticRoute> nmplStaticRoutes = nmplStaticRouteMapper.selectByExample(null);
        //业务服务
        for (NmplBusinessRoute nmplBusinessRoute : nmplBusinessRoutes) {
            ProxyBusinessRouteVo proxyBusinessRouteVo = new ProxyBusinessRouteVo();
            BeanUtils.copyProperties(nmplBusinessRoute,proxyBusinessRouteVo);
//            proxyBusinessRouteVo.setByteNetworkId(ArrayUtils.toObject(nmplBusinessRoute.getByteNetworkId()));
//            proxyBusinessRouteVo.setByteNetworkId(nmplBusinessRoute.getByteNetworkId());
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
     * 获取所有链路关系
     */
    private void getlinkVoList(ProxyResp proxyResp){
        NmplLinkExample example = new NmplLinkExample();
        List<NmplLink> links = nmplLinkMapper.selectByExample(example);

        /**
         * 获取链路的设备类型
         */
        List<LocalLinkVo> localLinkVos = new ArrayList<>();
        for (NmplLink nmplLink : links) {
            LocalLinkVo localLinkVo = new LocalLinkVo();
            BeanUtils.copyProperties(nmplLink, localLinkVo);
            localLinkVos.add(localLinkVo);
        }
        proxyResp.setLocalLinkVos(localLinkVos);
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

    /**
     * 获取定时任务配置信息
     */
    private void getDeviceTask(ProxyResp proxyResp){
        Set<String> deviceTypeSets = new HashSet<>();
        List<String> deviceIds = new ArrayList<>();
        Map<String,String> typeToId = new HashMap<>();
        for (ProxyBaseStationInfoVo proxyBaseStationInfoVo : proxyResp.getLocalStation()) {
            deviceTypeSets.add(proxyBaseStationInfoVo.getStationType());
            deviceIds.add(proxyBaseStationInfoVo.getStationId());
            typeToId.put(proxyBaseStationInfoVo.getStationType(),proxyBaseStationInfoVo.getStationId());
        }
        for (ProxyDeviceInfoVo localDeviceInfoVo : proxyResp.getLocalDeviceInfoVos()) {
            deviceTypeSets.add(localDeviceInfoVo.getDeviceType());
            deviceIds.add(localDeviceInfoVo.getDeviceId());
            typeToId.put(localDeviceInfoVo.getDeviceType(),localDeviceInfoVo.getDeviceId());
        }
        //获取所有的定时任务配置
        NmplTaskConfigExample nmplTaskConfigExample = new NmplTaskConfigExample();
        nmplTaskConfigExample.createCriteria().andDeviceTypeIn(new ArrayList<>(deviceTypeSets)).andIsExistEqualTo(true);
        List<NmplTaskConfig> nmplTaskConfigs = nmplTaskConfigMapper.selectByExample(nmplTaskConfigExample);

        NmplDeviceTaskConfigExample nmplDeviceTaskConfigExample = new NmplDeviceTaskConfigExample();
        nmplDeviceTaskConfigExample.createCriteria().andDeviceIdIn(deviceIds).andIsExistEqualTo(true);
        List<NmplDeviceTaskConfig> nmplDeviceTaskConfigs = nmplDeviceTaskConfigMapper.selectByExample(nmplDeviceTaskConfigExample);

        Map<String,NmplDeviceTaskConfig> map = new HashMap<>();
        for (NmplDeviceTaskConfig nmplDeviceTaskConfig : nmplDeviceTaskConfigs) {
            map.put(nmplDeviceTaskConfig.getTaskId(),nmplDeviceTaskConfig);
        }

        for (NmplTaskConfig nmplTaskConfig : nmplTaskConfigs) {
            TaskConfigVo taskConfigVo = new TaskConfigVo();
            Long localValue = nmplTaskConfig.getCurrentValue()!=null?nmplTaskConfig.getCurrentValue():nmplTaskConfig.getDefaultValue();
            if(map.containsKey(nmplTaskConfig.getTaskId())){
               localValue = map.get(nmplTaskConfig.getTaskId()).getCurrentValue()!=null?map.get(nmplTaskConfig.getTaskId()).getCurrentValue():localValue;
            }
            BeanUtils.copyProperties(nmplTaskConfig,taskConfigVo);
            taskConfigVo.setCurrentValue(localValue);
            taskConfigVo.setDeviceId(typeToId.get(nmplTaskConfig.getDeviceType()));
            proxyResp.getTaskConfigVoList().add(taskConfigVo);
        }
        log.info("TaskConfig query sucess");
    }

    /**
     * 获取定时任务配置信息
     */
    private void getDeviceConfig(ProxyResp proxyResp){
        Set<String> deviceTypeSets = new HashSet<>();
        List<String> deviceIds = new ArrayList<>();
        Map<String,String> typeToId = new HashMap<>();
        for (ProxyBaseStationInfoVo proxyBaseStationInfoVo : proxyResp.getLocalStation()) {
            deviceTypeSets.add(proxyBaseStationInfoVo.getStationType());
            deviceIds.add(proxyBaseStationInfoVo.getStationId());
            typeToId.put(proxyBaseStationInfoVo.getStationType(),proxyBaseStationInfoVo.getStationId());
        }
        for (ProxyDeviceInfoVo localDeviceInfoVo : proxyResp.getLocalDeviceInfoVos()) {
            deviceTypeSets.add(localDeviceInfoVo.getDeviceType());
            deviceIds.add(localDeviceInfoVo.getDeviceId());
            typeToId.put(localDeviceInfoVo.getDeviceType(),localDeviceInfoVo.getDeviceId());
        }
        //获取所有的定时任务配置
        NmplConfigExample nmplConfigExample = new NmplConfigExample();
        nmplConfigExample.createCriteria().andDeviceTypeIn(new ArrayList<>(deviceTypeSets)).andIsExistEqualTo(true);
        List<NmplConfig> nmplConfigs = nmplConfigMapper.selectByExample(nmplConfigExample);

        NmplDeviceConfigExample nmplDeviceConfigExample = new NmplDeviceConfigExample();
        nmplDeviceConfigExample.createCriteria().andDeviceIdIn(deviceIds).andIsExistEqualTo(true);
        List<NmplDeviceConfig> nmplDeviceConfigs = nmplDeviceConfigMapper.selectByExample(nmplDeviceConfigExample);

        Map<String,NmplDeviceConfig> map = new HashMap<>();
        for (NmplDeviceConfig nmplDeviceConfig : nmplDeviceConfigs) {
            map.put(nmplDeviceConfig.getConfigCode(),nmplDeviceConfig);
        }

        for (NmplConfig nmplConfig : nmplConfigs) {
            ConfigVo configVo = new ConfigVo();
            String localValue = nmplConfig.getConfigValue()!=null?nmplConfig.getConfigValue():nmplConfig.getDefaultValue();
            if(map.containsKey(nmplConfig.getConfigCode())){
                localValue = map.get(nmplConfig.getConfigCode()).getConfigValue()!=null?map.get(nmplConfig.getConfigCode()).getConfigValue():localValue;
            }
            BeanUtils.copyProperties(nmplConfig,configVo);
            configVo.setConfigValue(localValue);
            configVo.setDeviceId(typeToId.get(nmplConfig.getDeviceType()));
            proxyResp.getConfigVoList().add(configVo);
        }
        log.info("Config query sucess");
    }

}
