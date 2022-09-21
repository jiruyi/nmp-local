package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.request.InitInfoReq;
import com.matrictime.network.response.ProxyResp;
import com.matrictime.network.service.*;
import com.matrictime.network.util.HttpClientUtil;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;

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
                        ProxyResp proxyResp = (ProxyResp) resp.get("resultObj");
                        if (proxyResp.isExist()){
                            // 初始化本机基站
                            if (proxyResp.getLocalStation()!=null){
                                baseStationInfoService.initLocalInfo(proxyResp.getLocalStation());
                            }
                            // 初始化基站列表信息
                            if (!CollectionUtils.isEmpty(proxyResp.getBaseStationInfoList())){
                                baseStationInfoService.initInfo(proxyResp.getBaseStationInfoList());
                            }
                            // 初始化设备列表信息
                            if (!CollectionUtils.isEmpty(proxyResp.getDeviceInfoVos())){
                                deviceInfoService.initInfo(proxyResp.getDeviceInfoVos());
                            }
                            // 初始化本机设备信息
                            if (!CollectionUtils.isEmpty(proxyResp.getLocalDeviceInfoVos())){
                                deviceInfoService.initLocalInfo(proxyResp.getLocalDeviceInfoVos());
                            }
                            // 初始化路由列表信息
                            if (!CollectionUtils.isEmpty(proxyResp.getRoteVoList())){
                                routeService.initInfo(proxyResp.getRoteVoList());
                            }
                            // 初始化链路列表信息
                            if (!CollectionUtils.isEmpty(proxyResp.getLinkRelationVoList())){
                                linkRelationService.initInfo(proxyResp.getLinkRelationVoList());
                            }
                            // 初始化一体机列表信息
                            if (!CollectionUtils.isEmpty(proxyResp.getNmplOutlinePcInfoVos())){

                            }
                        }
                    }
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}
