package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.modelVo.CenterBaseStationInfoVo;
import com.matrictime.network.modelVo.CenterDeviceInfoVo;
import com.matrictime.network.modelVo.CenterLinkRelationVo;
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
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
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
//                String post = HttpClientUtil.post(ip + KEY_SPLIT + port + INIT_URL, jsonObject.toJSONString());
                String post = "{\n" +
                        "    \"success\": true,\n" +
                        "    \"resultObj\": {\n" +
                        "        \"baseStationInfoList\": [\n" +
                        "            \n" +
                        "            {\n" +
                        "                \"id\": 10,\n" +
                        "                \"stationId\": \"8250378057509568512\",\n" +
                        "                \"stationName\": \"测试推送\",\n" +
                        "                \"stationType\": \"01\",\n" +
                        "                \"enterNetworkTime\": null,\n" +
                        "                \"stationAdmain\": null,\n" +
                        "                \"remark\": \"487687\",\n" +
                        "                \"publicNetworkIp\": \"1.1.1.1\",\n" +
                        "                \"publicNetworkPort\": \"7585\",\n" +
                        "                \"lanIp\": \"192.168.72.14\",\n" +
                        "                \"lanPort\": \"7770\",\n" +
                        "                \"stationStatus\": \"04\",\n" +
                        "                \"stationNetworkId\": \"86-1000-1111-4544-1016\",\n" +
                        "                \"stationRandomSeed\": null,\n" +
                        "                \"relationOperatorId\": \"14\",\n" +
                        "                \"createUser\": \"4\",\n" +
                        "                \"createTime\": \"2022-08-23 19:20:26\",\n" +
                        "                \"updateUser\": null,\n" +
                        "                \"updateTime\": \"2022-08-23 19:43:36\",\n" +
                        "                \"isExist\": true\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"deviceInfoVos\": [\n" +
                        "            {\n" +
                        "                \"id\": 11,\n" +
                        "                \"deviceId\": \"8250384480469057536\",\n" +
                        "                \"deviceName\": \"测试秘钥中心\",\n" +
                        "                \"deviceType\": \"11\",\n" +
                        "                \"otherType\": null,\n" +
                        "                \"enterNetworkTime\": null,\n" +
                        "                \"deviceAdmain\": null,\n" +
                        "                \"remark\": null,\n" +
                        "                \"publicNetworkIp\": \"7.7.7.7\",\n" +
                        "                \"publicNetworkPort\": \"5868\",\n" +
                        "                \"lanIp\": \"192.168.72.14\",\n" +
                        "                \"lanPort\": \"2535\",\n" +
                        "                \"stationStatus\": \"04\",\n" +
                        "                \"stationNetworkId\": \"86-1000-1111-4544-1017\",\n" +
                        "                \"stationRandomSeed\": null,\n" +
                        "                \"relationOperatorId\": \"14\",\n" +
                        "                \"createUser\": \"4\",\n" +
                        "                \"createTime\": \"2022-08-23 19:45:57\",\n" +
                        "                \"updateUser\": null,\n" +
                        "                \"updateTime\": \"2022-08-23 19:49:06\",\n" +
                        "                \"isExist\": true\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"roteVoList\": [],\n" +
                        "        \"linkRelationVoList\": [\n" +
                        "            {\n" +
                        "                \"id\": 2,\n" +
                        "                \"linkName\": \"1\",\n" +
                        "                \"linkType\": \"02\",\n" +
                        "                \"mainDeviceId\": \"8250378057509568512\",\n" +
                        "                \"followDeviceId\": \"8250384480469057536\",\n" +
                        "                \"createUser\": \"4\",\n" +
                        "                \"createTime\": \"2022-09-23 10:34:43\",\n" +
                        "                \"updateUser\": null,\n" +
                        "                \"updateTime\": \"2022-09-23 10:36:33\",\n" +
                        "                \"isExist\": \"0\",\n" +
                        "                \"noticeDeviceType\": \"00\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\": 3,\n" +
                        "                \"linkName\": \"2\",\n" +
                        "                \"linkType\": \"02\",\n" +
                        "                \"mainDeviceId\": \"8250378057509568512\",\n" +
                        "                \"followDeviceId\": \"8249987461755764736\",\n" +
                        "                \"createUser\": \"4\",\n" +
                        "                \"createTime\": \"2022-09-23 10:37:00\",\n" +
                        "                \"updateUser\": null,\n" +
                        "                \"updateTime\": \"2022-09-23 10:37:00\",\n" +
                        "                \"isExist\": \"1\",\n" +
                        "                \"noticeDeviceType\": \"00\"\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"localStation\": {\n" +
                        "            \"id\": 10,\n" +
                        "            \"stationId\": \"8250378057509568512\",\n" +
                        "            \"stationName\": \"测试推送\",\n" +
                        "            \"stationType\": \"01\",\n" +
                        "            \"enterNetworkTime\": null,\n" +
                        "            \"stationAdmain\": null,\n" +
                        "            \"remark\": \"487687\",\n" +
                        "            \"publicNetworkIp\": \"1.1.1.1\",\n" +
                        "            \"publicNetworkPort\": \"7585\",\n" +
                        "            \"lanIp\": \"192.168.72.14\",\n" +
                        "            \"lanPort\": \"7770\",\n" +
                        "            \"stationStatus\": \"04\",\n" +
                        "            \"stationNetworkId\": \"86-1000-1111-4544-1016\",\n" +
                        "            \"stationRandomSeed\": null,\n" +
                        "            \"relationOperatorId\": \"14\",\n" +
                        "            \"createUser\": \"4\",\n" +
                        "            \"createTime\": \"2022-08-23 19:20:26\",\n" +
                        "            \"updateUser\": null,\n" +
                        "            \"updateTime\": \"2022-08-23 19:43:36\",\n" +
                        "            \"isExist\": true\n" +
                        "        },\n" +
                        "        \"localDeviceInfoVos\": [\n" +
                        "            {\n" +
                        "                \"id\": 11,\n" +
                        "                \"deviceId\": \"8250384480469057536\",\n" +
                        "                \"deviceName\": \"测试秘钥中心\",\n" +
                        "                \"deviceType\": \"11\",\n" +
                        "                \"otherType\": null,\n" +
                        "                \"enterNetworkTime\": null,\n" +
                        "                \"deviceAdmain\": null,\n" +
                        "                \"remark\": null,\n" +
                        "                \"publicNetworkIp\": \"7.7.7.7\",\n" +
                        "                \"publicNetworkPort\": \"5868\",\n" +
                        "                \"lanIp\": \"192.168.72.14\",\n" +
                        "                \"lanPort\": \"2535\",\n" +
                        "                \"stationStatus\": \"04\",\n" +
                        "                \"stationNetworkId\": \"86-1000-1111-4544-1017\",\n" +
                        "                \"stationRandomSeed\": null,\n" +
                        "                \"relationOperatorId\": \"14\",\n" +
                        "                \"createUser\": \"4\",\n" +
                        "                \"createTime\": \"2022-08-23 19:45:57\",\n" +
                        "                \"updateUser\": null,\n" +
                        "                \"updateTime\": \"2022-08-23 19:49:06\",\n" +
                        "                \"isExist\": true\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"nmplOutlinePcInfoVos\": [\n" +
                        "            {\n" +
                        "                \"id\": 1,\n" +
                        "                \"deviceId\": \"8261479920141664256\",\n" +
                        "                \"deviceName\": \"1\",\n" +
                        "                \"stationNetworkId\": \"1\",\n" +
                        "                \"remark\": \"1\",\n" +
                        "                \"createUser\": \"zyj\",\n" +
                        "                \"createTime\": \"2022-09-23 10:35:17\",\n" +
                        "                \"updateUser\": null,\n" +
                        "                \"updateTime\": \"2022-09-23 10:35:17\",\n" +
                        "                \"isExist\": true\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"exist\": true\n" +
                        "    },\n" +
                        "    \"errorCode\": null,\n" +
                        "    \"errorMsg\": null,\n" +
                        "    \"extendMsg\": null\n" +
                        "}";
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
                            // 初始化路由列表信息
//                            if (!CollectionUtils.isEmpty(proxyResp.getRouteVoList())){
//                                routeService.initInfo(proxyResp.getRouteVoList());
//                            }
                            // 初始化链路列表信息
                            JSONArray linkRelationVoList = resultObj.getJSONArray("linkRelationVoList");
                            List<CenterLinkRelationVo> centerLinkRelationVos = linkRelationVoList.toJavaList(CenterLinkRelationVo.class);
                            if (!CollectionUtils.isEmpty(centerLinkRelationVos)){
                                linkRelationService.initInfo(centerLinkRelationVos);
                            }
                            // 初始化一体机列表信息
//                            if (!CollectionUtils.isEmpty(proxyResp.getNmplOutlinePcInfoVos())){
//
//                            }
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
