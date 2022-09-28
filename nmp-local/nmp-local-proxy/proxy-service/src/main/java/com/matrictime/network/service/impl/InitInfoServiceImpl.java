package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.DeviceStatusEnum;
import com.matrictime.network.dao.domain.OutlinePcDomainService;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.CenterNmplOutlinePcInfoVo;
import com.matrictime.network.modelVo.OutlinePcVo;
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
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
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
                                for(CenterNmplOutlinePcInfoVo centerNmplOutlinePcInfoVo : proxyResp.getNmplOutlinePcInfoVos()){
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
            } catch (IOException e) {
                log.error(e.getMessage());
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
