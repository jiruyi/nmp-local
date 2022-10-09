package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.BusinessRouteDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.BusinessRouteRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BusinessRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.matrictime.network.base.constant.DataConstants.KEY_DEVICE_ID;

/**
 * @author by wangqiang
 * @date 2022/9/28.
 */
@Service
@Slf4j
public class BusinessRouteServiceImpl implements BusinessRouteService {

    @Resource
    private BusinessRouteDomainService businessRouteDomainService;

    @Resource
    private AsyncService asyncService;

    @Override
    public Result<Integer> insert(BusinessRouteRequest businessRouteRequest) {
        Result<Integer> result = new Result<>();
        try {
            businessRouteRequest.setRouteId(SnowFlake.nextId_String());
            businessRouteRequest.setCreateUser(RequestContext.getUser().getCreateUser());
            result.setResultObj(businessRouteDomainService.insert(businessRouteRequest));
            if(result.getResultObj() == DataConstants.INSERT_OR_UPDATE_SUCCESS){
                sendRoute(businessRouteRequest);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("insert:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> delete(BusinessRouteRequest businessRouteRequest) {
        Result<Integer> result = new Result<>();
        try {
            businessRouteRequest.setUpdateUser(RequestContext.getUser().getUpdateUser());
            result.setResultObj(businessRouteDomainService.delete(businessRouteRequest));
            if(result.getResultObj() == DataConstants.INSERT_OR_UPDATE_SUCCESS){
                sendRoute(businessRouteRequest);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("delete:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> update(BusinessRouteRequest businessRouteRequest) {
        Result<Integer> result = new Result<>();
        try {
            businessRouteRequest.setUpdateUser(RequestContext.getUser().getUpdateUser());
            result.setResultObj(businessRouteDomainService.update(businessRouteRequest));
            if(result.getResultObj() == DataConstants.INSERT_OR_UPDATE_SUCCESS){
                sendRoute(businessRouteRequest);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("update:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<PageInfo> select(BusinessRouteRequest businessRouteRequest) {
        Result<PageInfo> result = new Result<>();
        try {
            result.setResultObj(businessRouteDomainService.select(businessRouteRequest));
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("select:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<BaseStationInfoResponse> selectBaseStation(BaseStationInfoRequest baseStationInfoRequest) {
        Result<BaseStationInfoResponse> result = new Result<>();
        try {
            result.setResultObj(businessRouteDomainService.selectBaseStation(baseStationInfoRequest));
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("selectBaseStation:{}",e.getMessage());
        }
        return result;
    }

    //路由推送到各个基站
    private void sendRoute(BusinessRouteRequest businessRouteRequest) throws Exception {
        BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
        Result<BaseStationInfoResponse> baseStationInfoResponse = selectBaseStation(baseStationInfoRequest);
        List<BaseStationInfoVo> baseStationInfoList = baseStationInfoResponse.getResultObj().getBaseStationInfoList();
        //开启多线程
        for (BaseStationInfoVo baseStationInfoVo : baseStationInfoList) {
            Map<String,String> map = new HashMap<>();
            map.put(DataConstants.KEY_DATA, JSONObject.toJSONString(businessRouteRequest));
            String url = "http://"+baseStationInfoVo.getLanIp()+":"+baseStationInfoVo.getLanPort();
            map.put(DataConstants.KEY_URL,url);
            map.put(KEY_DEVICE_ID,baseStationInfoVo.getStationId());
            asyncService.httpPush(map);
        }
    }
}
