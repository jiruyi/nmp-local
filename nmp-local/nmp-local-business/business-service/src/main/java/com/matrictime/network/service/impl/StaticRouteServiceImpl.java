package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.StaticRouteDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.StaticRouteVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.StaticRouteRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BusinessRouteService;
import com.matrictime.network.service.InternetRouteService;
import com.matrictime.network.service.StaticRouteService;
import com.matrictime.network.util.CommonCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.matrictime.network.base.constant.DataConstants.KEY_DEVICE_ID;
import static com.matrictime.network.base.exception.ErrorMessageContants.IP_FORMAT_ERROR_MSG;

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
            if(!CommonCheckUtil.isIpv4Legal(staticRouteRequest.getServerIp())){
                throw new RuntimeException(IP_FORMAT_ERROR_MSG);
            }
            //字段校验
            if(!ObjectUtils.isEmpty(checkDataOnly(staticRouteRequest))){
                return checkDataOnly(staticRouteRequest);
            }
            staticRouteRequest.setCreateUser(RequestContext.getUser().getCreateUser());
            staticRouteRequest.setRouteId(SnowFlake.nextId_String());
            int insert = staticRouteDomainService.insert(staticRouteRequest);
            if(insert == DataConstants.INSERT_OR_UPDATE_SUCCESS){
                result.setResultObj(insert);
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
            int delete = staticRouteDomainService.delete(staticRouteRequest);
            if(delete == DataConstants.INSERT_OR_UPDATE_SUCCESS){
                result.setResultObj(delete);
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
            if(!CommonCheckUtil.isIpv4Legal(staticRouteRequest.getServerIp())){
                throw new RuntimeException(IP_FORMAT_ERROR_MSG);
            }
            //字段校验
            if(!ObjectUtils.isEmpty(checkDataOnly(staticRouteRequest))){
                return checkDataOnly(staticRouteRequest);
            }
            staticRouteRequest.setUpdateUser(RequestContext.getUser().getUpdateUser());
            int update = staticRouteDomainService.update(staticRouteRequest);
            if(update == DataConstants.INSERT_OR_UPDATE_SUCCESS){
                result.setResultObj(update);
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

    /**
     * 校验设备Id唯一性
     * @param staticRouteRequest
     * @return
     */
    private List<StaticRouteVo> checkStation(StaticRouteRequest staticRouteRequest){
        StaticRouteRequest checkStation = new StaticRouteRequest();
        checkStation.setStationId(staticRouteRequest.getStationId());
        checkStation.setNetworkId(staticRouteRequest.getNetworkId());
        PageInfo<StaticRouteVo> checkStationList = staticRouteDomainService.select(checkStation);
        return checkStationList.getList();
    }

    /**
     * 校验Ip唯一性
     * @param staticRouteRequest
     * @return
     */
    private List<StaticRouteVo> checkIp(StaticRouteRequest staticRouteRequest){
        StaticRouteRequest checkSeverIp = new StaticRouteRequest();
        checkSeverIp.setNetworkId(staticRouteRequest.getNetworkId());
        checkSeverIp.setServerIp(staticRouteRequest.getServerIp());
        PageInfo<StaticRouteVo> checkIpList = staticRouteDomainService.select(checkSeverIp);
        return checkIpList.getList();
    }

    private Result<Integer> checkDataOnly(StaticRouteRequest staticRouteRequest){
        List listStation = checkStation(staticRouteRequest);
        if(listStation.size() > NumberUtils.INTEGER_ZERO){
            return new Result<>(false, ErrorMessageContants.DEVICEID_REPEAT);
        }
        List listIp = checkIp(staticRouteRequest);
        if(listIp.size() > NumberUtils.INTEGER_ZERO){
            return new Result<>(false,ErrorMessageContants.IP_REPEAT);
        }
        return null;
    }

}
