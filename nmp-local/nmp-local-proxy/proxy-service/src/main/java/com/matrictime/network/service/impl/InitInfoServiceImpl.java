package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.ProxyConfigEnum;
import com.matrictime.network.dao.domain.ProxyConfigDomainService;
import com.matrictime.network.dao.mapper.NmplProxyConfigMapper;
import com.matrictime.network.dao.model.NmplProxyConfig;
import com.matrictime.network.facade.AlarmDataFacade;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.request.EditConfigReq;
import com.matrictime.network.request.ProxyReq;
import com.matrictime.network.response.ProxyResp;
import com.matrictime.network.service.*;
import com.matrictime.network.util.SystemUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@Slf4j
public class InitInfoServiceImpl extends SystemBaseService implements InitInfoService {

    @Autowired
    private BaseStationInfoService baseStationInfoService;

    @Autowired
    private DeviceInfoService deviceInfoService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private LinkRelationService linkRelationService;

    @Resource
    private OutlinePcService outlinePcService;

    @Autowired
    private AlarmDataFacade alarmDataFacade;

    @Autowired
    private ProxyConfigDomainService proxyConfigDomainService;

    @Autowired
    private TaskConfigService taskConfigService;

    @Autowired
    private ConfigService configService;

    @Override
//    @Transactional
    public void initInfo() {
        try {
            Set<String> localIps = SystemUtils.getLocalIp4Address();
            log.info("InitInfoServiceImpl.initInfo localIps:{}",localIps);
            if (CollectionUtils.isEmpty(localIps)){
                log.info("InitInfoServiceImpl.initInfo localIps isEmpty!");
                return;
            }
            ProxyReq proxyReq = new ProxyReq();
            proxyReq.setIps(localIps);

            ProxyResp initResult = alarmDataFacade.init(proxyReq);
            log.info("initInfo.post postRes:{}",JSONObject.toJSONString(initResult));
            if (!initResult.isExist()){
                log.info("InitInfoServiceImpl.initInfo local devices are not exist!");
                return;
            }

            NmplProxyConfig proxyConfig = new NmplProxyConfig();
            proxyConfig.setConfigCode(ProxyConfigEnum.LOCAL_IP.getCode());
            proxyConfig.setConfigDesc(ProxyConfigEnum.LOCAL_IP.getDesc());
            proxyConfig.setConfigValue(JSONObject.toJSONString(initResult.getLocalIps()));
            proxyConfigDomainService.addOrUpdateProxyConfig(proxyConfig);

            // 初始化本机基站
            List<ProxyBaseStationInfoVo> localStationList = initResult.getLocalStation();
            if (!CollectionUtils.isEmpty(localStationList)){
                List<CenterBaseStationInfoVo> centerBaseStationInfoVos = new ArrayList<>();
                for (int i=0;i<localStationList.size();i++){
                    CenterBaseStationInfoVo vo = new CenterBaseStationInfoVo();
                    BeanUtils.copyProperties(localStationList.get(i),vo);
                    centerBaseStationInfoVos.add(vo);
                }
                baseStationInfoService.initLocalInfo(centerBaseStationInfoVos);
            }

            // 初始化本机设备信息
            List<ProxyDeviceInfoVo> localDeviceInfoList = initResult.getLocalDeviceInfoVos();
            if (!CollectionUtils.isEmpty(localDeviceInfoList)){
                List<CenterDeviceInfoVo> centerDeviceInfoVos = new ArrayList<>();
                for (int i=0;i<localDeviceInfoList.size();i++){
                    CenterDeviceInfoVo vo = new CenterDeviceInfoVo();
                    BeanUtils.copyProperties(localDeviceInfoList.get(i),vo);
                    centerDeviceInfoVos.add(vo);
                }
                deviceInfoService.initLocalInfo(centerDeviceInfoVos);
            }

            // 初始化基站列表信息
            List<ProxyBaseStationInfoVo> baseStationInfoList = initResult.getBaseStationInfoList();
            if (!CollectionUtils.isEmpty(baseStationInfoList)){
                List<CenterBaseStationInfoVo> centerBaseStationInfoVos = new ArrayList<>();
                for (int i=0;i<baseStationInfoList.size();i++){
                    CenterBaseStationInfoVo vo = new CenterBaseStationInfoVo();
                    BeanUtils.copyProperties(baseStationInfoList.get(i),vo);
                    centerBaseStationInfoVos.add(vo);
                }
                baseStationInfoService.initInfo(centerBaseStationInfoVos);
            }

            // 初始化设备列表信息
            List<ProxyDeviceInfoVo> deviceInfoList = initResult.getDeviceInfoVos();
            if (!CollectionUtils.isEmpty(deviceInfoList)){
                List<CenterDeviceInfoVo> centerDeviceInfoVos = new ArrayList<>();
                for (int i=0;i<deviceInfoList.size();i++){
                    CenterDeviceInfoVo vo = new CenterDeviceInfoVo();
                    BeanUtils.copyProperties(deviceInfoList.get(i),vo);
                    centerDeviceInfoVos.add(vo);
                }
                deviceInfoService.initInfo(centerDeviceInfoVos);
            }

            // 初始化业务路由列表信息
            List<ProxyBusinessRouteVo> businessRouteVoList = initResult.getBusinessRouteVoList();
            if (!CollectionUtils.isEmpty(businessRouteVoList)){
                List<NmplBusinessRouteVo> nmplBusinessRouteVos = new ArrayList<>();
                for (int i=0;i<businessRouteVoList.size();i++){
                    NmplBusinessRouteVo vo = new NmplBusinessRouteVo();
                    BeanUtils.copyProperties(businessRouteVoList.get(i),vo);
                    nmplBusinessRouteVos.add(vo);
                }
                routeService.businessRouteInitInfo(nmplBusinessRouteVos);
            }

            //初始化出网路由
            List<ProxyInternetRouteVo> internetRouteVoList = initResult.getInternetRouteVoList();
            if (!CollectionUtils.isEmpty(internetRouteVoList)){
                List<NmplInternetRouteVo> nmplInternetRouteVos = new ArrayList<>();
                for (int i=0;i<internetRouteVoList.size();i++){
                    NmplInternetRouteVo vo = new NmplInternetRouteVo();
                    BeanUtils.copyProperties(internetRouteVoList.get(i),vo);
                    nmplInternetRouteVos.add(vo);
                }
                routeService.internetRouteInitInfo(nmplInternetRouteVos);
            }

            //初始化静态路由
            List<ProxyStaticRouteVo> staticRouteVoList = initResult.getStaticRouteVoList();
            if (!CollectionUtils.isEmpty(staticRouteVoList)){
                List<NmplStaticRouteVo> nmplStaticRouteVos = new ArrayList<>();
                for (int i=0;i<staticRouteVoList.size();i++){
                    NmplStaticRouteVo vo = new NmplStaticRouteVo();
                    BeanUtils.copyProperties(staticRouteVoList.get(i),vo);
                    nmplStaticRouteVos.add(vo);
                }
                routeService.staticRouteInitInfo(nmplStaticRouteVos);
            }

            // 初始化链路列表信息
            List<LocalLinkVo> localLinkVoList = initResult.getLocalLinkVos();
            if (!CollectionUtils.isEmpty(localLinkVoList)){
                List<ProxyLinkVo> localLinkVos = new ArrayList<>();
                for (int i=0;i<localLinkVoList.size();i++){
                    ProxyLinkVo vo = new ProxyLinkVo();
                    BeanUtils.copyProperties(localLinkVoList.get(i),vo);
                    localLinkVos.add(vo);
                }
                linkRelationService.initInfo(localLinkVos);
            }

            // 初始化一体机列表信息
            List<NmplOutlinePcInfoVo> nmplOutlinePcInfoVoList = initResult.getNmplOutlinePcInfoVos();
            if (!CollectionUtils.isEmpty(nmplOutlinePcInfoVoList)){
                List<CenterNmplOutlinePcInfoVo> centerNmplOutlinePcInfoVos = new ArrayList<>();
                for (int i=0;i<nmplOutlinePcInfoVoList.size();i++){
                    CenterNmplOutlinePcInfoVo vo = new CenterNmplOutlinePcInfoVo();
                    BeanUtils.copyProperties(nmplOutlinePcInfoVoList.get(i),vo);
                    centerNmplOutlinePcInfoVos.add(vo);
                }
                outlinePcService.initInfo(centerNmplOutlinePcInfoVos);
            }

            // 初始化定时任务配置列表信息
            List<TaskConfigVo> taskConfigVoList = initResult.getTaskConfigVoList();
            if (!CollectionUtils.isEmpty(taskConfigVoList)){
                List<CenterTaskConfigVo> centerTaskConfigVos = new ArrayList<>();
                for (int i=0;i<taskConfigVoList.size();i++){
                    CenterTaskConfigVo vo = new CenterTaskConfigVo();
                    BeanUtils.copyProperties(taskConfigVoList.get(i),vo);
                    centerTaskConfigVos.add(vo);
                }
                taskConfigService.initInfo(centerTaskConfigVos);
            }

            List<ConfigVo> configVoList = initResult.getConfigVoList();
            if (!CollectionUtils.isEmpty(configVoList)){
                List<CenterConfigVo> centerConfigVos = new ArrayList<>();
                for (int i=0;i<configVoList.size();i++){
                    CenterConfigVo vo = new CenterConfigVo();
                    BeanUtils.copyProperties(configVoList.get(i),vo);
                    centerConfigVos.add(vo);
                }
                configService.initInfo(centerConfigVos);
            }
        } catch (Exception e) {
            log.error("InitInfoServiceImpl.initInfo:{}",e);
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }


}
