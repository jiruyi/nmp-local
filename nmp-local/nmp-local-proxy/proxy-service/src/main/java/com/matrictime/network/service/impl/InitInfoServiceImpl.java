package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.base.enums.DeviceStatusEnum;
import com.matrictime.network.dao.domain.OutlinePcDomainService;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.InitInfoReq;
import com.matrictime.network.request.OutlinePcReq;
import com.matrictime.network.response.ProxyResp;
import com.matrictime.network.service.*;
import com.matrictime.network.util.HttpClientUtil;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.INIT_URL;
import static com.matrictime.network.base.constant.DataConstants.KEY_SPLIT;

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


    @Value("${netmanage.ip}")
    private String ip;

    @Value("${netmanage.port}")
    private String port;

    @Override
    @Transactional
    public void initInfo(InitInfoReq req) {
        String localIp = req.getLocalIp();
        if (!ParamCheckUtil.checkVoStrBlank(localIp)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ip",localIp);
            try {
                String post = HttpClientUtil.post(ip + KEY_SPLIT + port + INIT_URL, jsonObject.toJSONString());
                log.info("initInfo.post ip:{}  *******  postRes:{}",localIp,post);
                if (!ParamCheckUtil.checkVoStrBlank(post)){
                    JSONObject resp = JSONObject.parseObject(post);
                    if (resp.containsKey("success") && (Boolean)resp.get("success")){
                        JSONObject resultObj = resp.getJSONObject("resultObj");
                        boolean isExist = resultObj.getBooleanValue("exist");
                        if (isExist){
                            // 初始化本机基站
                            CenterBaseStationInfoVo localStation = resultObj.getObject("localStation", CenterBaseStationInfoVo.class);
                            if (localStation!=null){
                                baseStationInfoService.initLocalInfo(localStation);
                            }
                            // 初始化基站列表信息
                            JSONArray baseStationInfoList = resultObj.getJSONArray("baseStationInfoList");
                            List<CenterBaseStationInfoVo> centerBaseStationInfoVos = baseStationInfoList.toJavaList(CenterBaseStationInfoVo.class);
                            if (!CollectionUtils.isEmpty(centerBaseStationInfoVos)){
                                baseStationInfoService.initInfo(centerBaseStationInfoVos);
                            }
                            // 初始化设备列表信息
                            JSONArray deviceInfoVos = resultObj.getJSONArray("deviceInfoVos");
                            List<CenterDeviceInfoVo> centerDeviceInfoVos = deviceInfoVos.toJavaList(CenterDeviceInfoVo.class);
                            if (!CollectionUtils.isEmpty(centerDeviceInfoVos)){
                                deviceInfoService.initInfo(centerDeviceInfoVos);
                            }
                            // 初始化本机设备信息
                            JSONArray localDeviceInfoVos = resultObj.getJSONArray("localDeviceInfoVos");
                            List<CenterDeviceInfoVo> centerDeviceInfoVoList = localDeviceInfoVos.toJavaList(CenterDeviceInfoVo.class);
                            if (!CollectionUtils.isEmpty(centerDeviceInfoVoList)){
                                deviceInfoService.initLocalInfo(centerDeviceInfoVoList);
                            }
                            // 初始化业务路由列表信息
                            JSONArray businessRouteVos = resultObj.getJSONArray("businessRouteVoList");
                            List<NmplBusinessRouteVo> nmplBusinessRouteVos = businessRouteVos.toJavaList(NmplBusinessRouteVo.class);
                            if (!CollectionUtils.isEmpty(nmplBusinessRouteVos)){
                                routeService.businessRouteInitInfo(nmplBusinessRouteVos);
                            }
                            //初始化出网路由
                            JSONArray internetRouteVos = resultObj.getJSONArray("internetRouteVoList");
                            List<NmplInternetRouteVo> nmplInternetRouteVos = internetRouteVos.toJavaList(NmplInternetRouteVo.class);
                            if (!CollectionUtils.isEmpty(nmplInternetRouteVos)){
                                routeService.internetRouteInitInfo(nmplInternetRouteVos);
                            }
                            //初始化静态路由
                            JSONArray staticRouteVos = resultObj.getJSONArray("staticRouteVoList");
                            List<NmplStaticRouteVo> nmplStaticRouteVos = staticRouteVos.toJavaList(NmplStaticRouteVo.class);
                            if (!CollectionUtils.isEmpty(nmplStaticRouteVos)){
                                routeService.staticRouteInitInfo(nmplStaticRouteVos);
                            }
                            // 初始化链路列表信息
                            JSONArray linkRelationVoList = resultObj.getJSONArray("linkRelationVoList");
                            List<LinkVo> linkVos = linkRelationVoList.toJavaList(LinkVo.class);
                            if (!CollectionUtils.isEmpty(linkVos)){
                                linkRelationService.initInfo(linkVos);
                            }
                            // 初始化一体机列表信息
                            JSONArray nmplOutlinePcInfoList = resultObj.getJSONArray("nmplOutlinePcInfoVos");
                            List<CenterNmplOutlinePcInfoVo> centerNmplOutlinePcInfoVos = nmplOutlinePcInfoList.toJavaList(CenterNmplOutlinePcInfoVo.class);
                            if (!CollectionUtils.isEmpty(centerNmplOutlinePcInfoVos)){
                                outlinePcService.initInfo(centerNmplOutlinePcInfoVos);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
    }


}
