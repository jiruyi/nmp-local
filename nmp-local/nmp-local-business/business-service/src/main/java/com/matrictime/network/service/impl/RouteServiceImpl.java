package com.matrictime.network.service.impl;

import com.matrictime.network.config.RestTemplateContextConfig;
import com.matrictime.network.config.ThreadPoolContextConfig;
import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.domain.RouteDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.RouteVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.RouteRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
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

    @Resource
    private ThreadPoolContextConfig threadPoolContextConfig;

    @Override
    public Result<Integer> insertRoute(RouteRequest routeRequest) {
        Result<Integer> result = new Result();
        Date date = new Date();
        List<Future<Result>> results = new LinkedList<Future<Result>>();
        BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
        try {
            routeRequest.setCreateTime(getFormatDate(date));
            Integer insetFlag = routeDomainService.insertRoute(routeRequest);
            if(insetFlag != null){
                List<BaseStationInfoVo> baseStationInfoVoList =
                        baseStationInfoDomainService.selectLinkBaseStationInfo(baseStationInfoRequest);
                SendRouteCallable sendRouteCallable = new SendRouteCallable(baseStationInfoVoList,routeRequest);
                for(int poolIndex = 0;poolIndex<5;poolIndex ++){
                    Future<Result> future = threadPoolContextConfig.getThreadPoolExecutor().submit(sendRouteCallable);
                    results.add(future);
                }
            }
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
            result.setResultObj(routeDomainService.updateRoute(routeRequest));
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

    class SendRouteCallable implements Callable<Result> {

        private List<BaseStationInfoVo> ipList;

        private RouteRequest postForEntity;

        public SendRouteCallable(List<BaseStationInfoVo> ipList, RouteRequest postForEntity) {
            this.ipList = ipList;
            this.postForEntity = postForEntity;
        }

        @Override
        public Result call() throws Exception {
            for(int ipIndex = 0;ipIndex<ipList.size();ipIndex ++){
                ResponseEntity<Result> routeVoResponseEntity = restTemplateContextConfig.getRestTemplate().
                        postForEntity("192.168.72.47:8002/nmp-local-business/menu/queryAllMenu", postForEntity, Result.class);
                if(routeVoResponseEntity == null){
                    return new Result(false,"失败");
                }
            }
            return new Result(true,"成功");
        }
    }


    private String getFormatDate(Date date){
        String creatDate = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat creatDateToString = new SimpleDateFormat(creatDate);
        return creatDateToString.format(date);
    }
}