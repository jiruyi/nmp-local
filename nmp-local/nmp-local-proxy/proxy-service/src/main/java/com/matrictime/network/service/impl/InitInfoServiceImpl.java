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
    private OutlinePcDomainService outlinePcDomainService;


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
                            JSONArray nmplOutlinePcInfoList = resultObj.getJSONArray("nmplOutlinePcInfoVos");
                            List<CenterNmplOutlinePcInfoVo> centerNmplOutlinePcInfoVos = nmplOutlinePcInfoList.toJavaList(CenterNmplOutlinePcInfoVo.class);
                            if (!CollectionUtils.isEmpty(centerNmplOutlinePcInfoVos)){
                                for(CenterNmplOutlinePcInfoVo centerNmplOutlinePcInfoVo : centerNmplOutlinePcInfoVos){
                                    BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
                                    baseStationInfoRequest.setStationId(centerNmplOutlinePcInfoVo.getDeviceId());
                                    List<BaseStationInfoVo> baseStationInfoVos = outlinePcDomainService.
                                            selectBaseStation(baseStationInfoRequest);
                                    //station表中没有该数据
                                    if(baseStationInfoVos.size() <= NumberUtils.INTEGER_ZERO ||
                                            !isActive(baseStationInfoVos.get(NumberUtils.INTEGER_ZERO))){
                                        outlinePcDomainService.insertOutlinePc(changeData(centerNmplOutlinePcInfoVo));
                                    }
                                    //station表中有该数据
                                    if(baseStationInfoVos.size() > NumberUtils.INTEGER_ZERO &&
                                            isActive(baseStationInfoVos.get(NumberUtils.INTEGER_ZERO))){
                                        compareData(centerNmplOutlinePcInfoVo);
                                    }
                                }
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

    /**
     * 比较一体机中是否有该数据然后选择插入还是更新
     * @param centerNmplOutlinePcInfoVo
     */
    private void compareData(CenterNmplOutlinePcInfoVo centerNmplOutlinePcInfoVo){
        List<OutlinePcVo> outlinePcVos = outlinePcDomainService.
                selectOutlinePc(changeData(centerNmplOutlinePcInfoVo));
        if(outlinePcVos.size() > NumberUtils.INTEGER_ZERO){
            outlinePcDomainService.updateOutlinePc(changeData(centerNmplOutlinePcInfoVo));
        }else {
            outlinePcDomainService.insertOutlinePc(changeData(centerNmplOutlinePcInfoVo));
        }
    }

    /**
     * 判断station是否激活
     * @param baseStationInfoVo
     * @return
     */
    private boolean isActive(BaseStationInfoVo baseStationInfoVo){
        if(DeviceStatusEnum.ACTIVE.equals(baseStationInfoVo.getStationStatus())){
            return true;
        }
        return false;
    }

    /**
     * 将CenterNmplOutlinePcInfoVo转换成OutlinePcReq
     * @param centerNmplOutlinePcInfoVo
     * @return
     */
    private OutlinePcReq changeData(CenterNmplOutlinePcInfoVo centerNmplOutlinePcInfoVo){
        OutlinePcReq outlinePcReq = new OutlinePcReq();
        BeanUtils.copyProperties(centerNmplOutlinePcInfoVo,outlinePcReq);
        return outlinePcReq;
    }
}
