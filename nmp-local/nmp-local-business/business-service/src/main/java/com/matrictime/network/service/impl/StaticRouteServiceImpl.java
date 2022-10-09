package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.StaticRouteDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.StaticRouteRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BusinessRouteService;
import com.matrictime.network.service.InternetRouteService;
import com.matrictime.network.service.StaticRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.matrictime.network.base.constant.DataConstants.KEY_DEVICE_ID;

/**
 * @author by wangqiang
 * @date 2022/10/9.
 */
@Service
@Slf4j
public class StaticRouteServiceImpl implements StaticRouteService {

    @Resource
    private StaticRouteDomainService staticRouteDomainService;

    @Resource
    private BusinessRouteService businessRouteService;

    @Resource
    private AsyncService asyncService;

    @Override
    public Result<Integer> insert(StaticRouteRequest staticRouteRequest) {
        Result<Integer> result = new Result<>();
        try {
            staticRouteRequest.setCreateUser(RequestContext.getUser().getCreateUser());
            staticRouteRequest.setRouteId(SnowFlake.nextId_String());
            result.setResultObj(staticRouteDomainService.insert(staticRouteRequest));
            if(result.getResultObj() == DataConstants.INSERT_OR_UPDATE_SUCCESS){
                sendRoute(staticRouteRequest);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("insert:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> delete(StaticRouteRequest staticRouteRequest) {
        Result<Integer> result = new Result<>();
        try {
            staticRouteRequest.setUpdateUser(RequestContext.getUser().getUpdateUser());
            result.setResultObj(staticRouteDomainService.delete(staticRouteRequest));
            if(result.getResultObj() == DataConstants.INSERT_OR_UPDATE_SUCCESS){
                sendRoute(staticRouteRequest);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("delete:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> update(StaticRouteRequest staticRouteRequest) {
        Result<Integer> result = new Result<>();
        try {
            staticRouteRequest.setUpdateUser(RequestContext.getUser().getUpdateUser());
            result.setResultObj(staticRouteDomainService.update(staticRouteRequest));
            if(result.getResultObj() == DataConstants.INSERT_OR_UPDATE_SUCCESS){
                sendRoute(staticRouteRequest);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("update:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<PageInfo> select(StaticRouteRequest staticRouteRequest) {
        Result<PageInfo> result = new Result<>();
        try {
            result.setResultObj(staticRouteDomainService.select(staticRouteRequest));
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("select:{}",e.getMessage());
        }
        return result;
    }

    //路由推送到各个基站
    private void sendRoute(StaticRouteRequest staticRouteRequest) throws Exception {
        BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
        Result<BaseStationInfoResponse> baseStationInfoResponse = businessRouteService.selectBaseStation(baseStationInfoRequest);
        List<BaseStationInfoVo> baseStationInfoList = baseStationInfoResponse.getResultObj().getBaseStationInfoList();
        //开启多线程
        for (BaseStationInfoVo baseStationInfoVo : baseStationInfoList) {
            Map<String,String> map = new HashMap<>();
            map.put(DataConstants.KEY_DATA, JSONObject.toJSONString(staticRouteRequest));
            String url = "http://"+baseStationInfoVo.getLanIp()+":"+baseStationInfoVo.getLanPort();
            map.put(DataConstants.KEY_URL,url);
            map.put(KEY_DEVICE_ID,baseStationInfoVo.getStationId());
            asyncService.httpPush(map);
        }
    }
}
