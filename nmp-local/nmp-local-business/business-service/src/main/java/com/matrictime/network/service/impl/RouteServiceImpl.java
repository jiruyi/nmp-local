package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.domain.RouteDomainService;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.model.NmplDeviceInfo;
import com.matrictime.network.dao.model.NmplDeviceInfoExample;
import com.matrictime.network.dao.model.extend.NmplDeviceInfoExt;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.RouteVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.RouteRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class RouteServiceImpl implements RouteService {
    @Resource
    private RouteDomainService routeDomainService;

    @Resource
    private BaseStationInfoDomainService baseStationInfoDomainService;

    @Value("${proxy.port}")
    private String port;

    @Value("${proxy.context-path}")
    private String contextPath;

    @Resource
    private AsyncService asyncService;

    @Override
    public Result<Integer> insertRoute(RouteRequest routeRequest) {
        Result<Integer> result = new Result();
        Date date = new Date();
        try {
            routeRequest.setCreateTime(getFormatDate(date));
            routeRequest.setUpdateTime(getFormatDate(date));
            routeRequest.setCreateUser(RequestContext.getUser().getUserId().toString());
            Integer insetFlag = routeDomainService.insertRoute(routeRequest);
            if(insetFlag == 2){
                return new Result<>(false,"路由不可以重复插入");
            }
            sendRout(routeRequest,DataConstants.URL_ROUTE_INSERT);
            result.setResultObj(insetFlag);
            result.setSuccess(true);
        }catch (Exception e){
            log.info("创建路由异常",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg("创建路由异常");
        }
        return result;
    }

    @Override
    public Result<Integer> deleteRoute(RouteRequest routeRequest) {
        Result<Integer> result = new Result<>();
        try {
            result.setResultObj(routeDomainService.deleteRoute(routeRequest));
            result.setSuccess(true);
            //sendRout(routeRequest,DataConstants.URL_ROUTE_DELETE);
        }catch (Exception e){
            result.setErrorMsg("参数异常");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<Integer> updateRoute(RouteRequest routeRequest) {
        Result<Integer> result = new Result<>();
        Date date = new Date();
        try {
            routeRequest.setUpdateTime(getFormatDate(date));
            routeRequest.setCreateUser(RequestContext.getUser().getUserId().toString());
            Integer updateFlag = routeDomainService.updateRoute(routeRequest);
            if(updateFlag == 2){
                return new Result<>(false,"路由不可以重复插入");
            }
            sendRout(routeRequest,DataConstants.URL_ROUTE_UPDATE);
            result.setResultObj(updateFlag);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg("参数异常");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<PageInfo<RouteVo>> selectRoute(RouteRequest routeRequest) {
        Result<PageInfo<RouteVo>> result = new Result<>();
        try {
            result.setResultObj(routeDomainService.selectRoute(routeRequest));
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("参数异常");
        }
        return result;
    }

    @Override
    public List<NmplDeviceInfoExt> selectDevices() {
        List<NmplDeviceInfoExt> deviceInfoExtList;
        deviceInfoExtList = routeDomainService.selectDevices();
        return deviceInfoExtList;
    }

    /**
     * 多线程推送
     * @param routeRequest
     * @throws Exception
     */
    private void sendRout(RouteRequest routeRequest,String suffix) throws Exception {
        //获取基站信息
        BaseStationInfoRequest accessBaseStationInfoRequest = new BaseStationInfoRequest();
        accessBaseStationInfoRequest.setStationId(routeRequest.getAccessDeviceId());

        BaseStationInfoRequest boundaryBaseStationInfoRequest = new BaseStationInfoRequest();
        boundaryBaseStationInfoRequest.setStationId(routeRequest.getBoundaryDeviceId());

        List<BaseStationInfoVo> accessBaseStationList =
                baseStationInfoDomainService.selectLinkBaseStationInfo(accessBaseStationInfoRequest);
        List<BaseStationInfoVo> boundaryBaseStationList =
                baseStationInfoDomainService.selectLinkBaseStationInfo(boundaryBaseStationInfoRequest);

        if(accessBaseStationList.size() > 0 && boundaryBaseStationList.size() > 0){
            List<BaseStationInfoVo> unionList = ListUtils.union(accessBaseStationList,boundaryBaseStationList);
            //开启多线程
            for (BaseStationInfoVo baseStationInfoVo : unionList) {
                Map<String,String> map = new HashMap<>();
                map.put(DataConstants.KEY_DEVICE_ID,baseStationInfoVo.getStationId());
                JSONObject jsonReq = new JSONObject();
                jsonReq.put("infoVos",baseStationInfoVo);
                map.put(DataConstants.KEY_DATA,jsonReq.toJSONString());
                String url = "http://"+baseStationInfoVo.getLanIp()+":"+port+contextPath+suffix;
                map.put(DataConstants.KEY_URL,url);
                asyncService.httpPush(map);
            }
        }

    }




    private String getFormatDate(Date date){
        String creatDate = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat creatDateToString = new SimpleDateFormat(creatDate);
        return creatDateToString.format(date);
    }


}