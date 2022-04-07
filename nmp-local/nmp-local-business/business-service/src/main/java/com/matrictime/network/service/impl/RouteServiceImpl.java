package com.matrictime.network.service.impl;

import com.matrictime.network.config.RestTemplateContextConfig;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.domain.RouteDomainService;
import com.matrictime.network.dao.model.extend.NmplDeviceInfoExt;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.RouteVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.RouteRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.RouteService;
import com.matrictime.network.util.ListSplitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

@Service
@Slf4j
public class RouteServiceImpl implements RouteService {
    @Resource
    private RouteDomainService routeDomainService;

    @Resource
    private BaseStationInfoDomainService baseStationInfoDomainService;

    @Resource
    private RestTemplateContextConfig restTemplateContextConfig;

    private final String HTTP_URL = "HTTP://";

    @Override
    public Result<Integer> insertRoute(RouteRequest routeRequest) {
        Result<Integer> result = new Result();
        Date date = new Date();
        try {
            routeRequest.setCreateTime(getFormatDate(date));
            routeRequest.setCreateUser(RequestContext.getUser().getNickName());
            Integer insetFlag = routeDomainService.insertRoute(routeRequest);
            //List<Future<List<Result>>> futures = collectResult(routeRequest);
            result.setResultObj(insetFlag);
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> deleteRoute(RouteRequest routeRequest) {
        Result<Integer> result = new Result<>();
        try {
            result.setResultObj(routeDomainService.deleteRoute(routeRequest));
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
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
            routeRequest.setUpdateUser(RequestContext.getUser().getNickName());
            Integer updateFlag = routeDomainService.updateRoute(routeRequest);
            //List<Future<List<Result>>> futures = collectResult(routeRequest);
            result.setResultObj(updateFlag);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
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
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public List<NmplDeviceInfoExt> selectDevices() {
        List<NmplDeviceInfoExt> deviceInfoExtList;
        deviceInfoExtList = routeDomainService.selectDevices();
        return deviceInfoExtList;
    }

    /*
    * 多线程推送路由信息
    * */
    @Async("asyncServiceExecutor")
    public Future<List<Result>> sendRoute(List<BaseStationInfoVo> ipList,RouteRequest postForEntity){
        List<Result> resultList = new ArrayList<>();
        for(int ipIndex = 0;ipIndex<ipList.size();ipIndex ++){
            ResponseEntity<Result> routeVoResponseEntity = restTemplateContextConfig.getRestTemplate().
                    postForEntity(HTTP_URL + ipList.get(ipIndex).getPublicNetworkIp()+":"+
                            ipList.get(ipIndex).getPublicNetworkPort()+"/nmp-local-business/menu/queryAllMenu",
                            postForEntity, Result.class);
            resultList.add(routeVoResponseEntity.getBody());
        }
        return new AsyncResult<>(resultList);
    }

    /*
      获取多线程推送后的返回值
    * */
    private List<Future<List<Result>>> collectResult(RouteRequest routeRequest){
        BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
        List<Future<List<Result>>> futureLinkedList = new LinkedList<>();
        List<BaseStationInfoVo> baseStationInfoVoList =
                baseStationInfoDomainService.selectLinkBaseStationInfo(baseStationInfoRequest);
        List<List<BaseStationInfoVo>> list = ListSplitUtil.split(baseStationInfoVoList,10);
        for(int poolIndex = 0;poolIndex<list.size();poolIndex ++){
            Future<List<Result>> sendRoute = sendRoute(list.get(poolIndex), routeRequest);
            futureLinkedList.add(sendRoute);
        }
        return futureLinkedList;
    }


    private String getFormatDate(Date date){
        String creatDate = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat creatDateToString = new SimpleDateFormat(creatDate);
        return creatDateToString.format(date);
    }


}