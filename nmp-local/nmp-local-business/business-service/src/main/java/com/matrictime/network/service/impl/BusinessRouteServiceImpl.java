package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.BusinessRouteDomainService;
import com.matrictime.network.dao.mapper.NmplBusinessRouteMapper;
import com.matrictime.network.dao.model.NmplBusinessRoute;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.BusinessRouteVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.BusinessRouteRequest;
import com.matrictime.network.request.StaticRouteRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BusinessRouteService;
import com.matrictime.network.util.CommonCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.matrictime.network.base.constant.DataConstants.KEY_DEVICE_ID;
import static com.matrictime.network.base.exception.ErrorMessageContants.IP_FORMAT_ERROR_MSG;

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

    @Resource
    private NmplBusinessRouteMapper nmplBusinessRouteMapper;

    @Value("${proxy.port}")
    private String port;

    @Value("${proxy.context-path}")
    private String contextPath;

    @Override
    public Result<Integer> insert(BusinessRouteRequest businessRouteRequest) {
        Result<Integer> result = new Result<>();
        try {
            if(!CommonCheckUtil.isIpv4Legal(businessRouteRequest.getIp())){
                throw new RuntimeException(IP_FORMAT_ERROR_MSG);
            }
            if(!ObjectUtils.isEmpty(checkDataOnly(businessRouteRequest))){
                return checkDataOnly(businessRouteRequest);
            }
            businessRouteRequest.setRouteId(SnowFlake.nextId_String());
            businessRouteRequest.setCreateUser(RequestContext.getUser().getCreateUser());
            int insert = businessRouteDomainService.insert(businessRouteRequest);
            if(insert == DataConstants.INSERT_OR_UPDATE_SUCCESS){
                result.setResultObj(insert);
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
            int delete = businessRouteDomainService.delete(businessRouteRequest);
            if(delete == DataConstants.INSERT_OR_UPDATE_SUCCESS){
                result.setResultObj(delete);
                //sendRoute(businessRouteRequest,DataConstants.URL_ROUTE_DELETE);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("delete:{}",e.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public Result<Integer> update(BusinessRouteRequest businessRouteRequest) {
        Result<Integer> result = new Result<>();
        try {
            if(!CommonCheckUtil.isIpv4Legal(businessRouteRequest.getIp())){
                throw new RuntimeException(IP_FORMAT_ERROR_MSG);
            }
            if(!ObjectUtils.isEmpty(checkDataOnly(businessRouteRequest))){
                return checkDataOnly(businessRouteRequest);
            }
            businessRouteRequest.setUpdateUser(RequestContext.getUser().getUpdateUser());
            int update = businessRouteDomainService.update(businessRouteRequest);
            if(update == DataConstants.INSERT_OR_UPDATE_SUCCESS){
                result.setResultObj(update);
                NmplBusinessRoute nmplBusinessRoute = nmplBusinessRouteMapper.selectByPrimaryKey(businessRouteRequest.getId());
                sendRoute(nmplBusinessRoute,DataConstants.UPDATE_BUSINESS_ROUTE);
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
    private void sendRoute(NmplBusinessRoute nmplBusinessRoute,String suffix) throws Exception {
        BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
        Result<BaseStationInfoResponse> baseStationInfoResponse = selectBaseStation(baseStationInfoRequest);
        List<BaseStationInfoVo> baseStationInfoList = baseStationInfoResponse.getResultObj().getBaseStationInfoList();
        //开启多线程
        for (BaseStationInfoVo baseStationInfoVo : baseStationInfoList) {
            Map<String,String> map = new HashMap<>();
            map.put(DataConstants.KEY_DATA, JSONObject.toJSONString(nmplBusinessRoute));
            String url = "http://"+baseStationInfoVo.getLanIp()+":"+port+contextPath + suffix;
            map.put(DataConstants.KEY_URL,url);
            map.put(KEY_DEVICE_ID,baseStationInfoVo.getStationId());
            asyncService.httpPush(map);
        }
    }

    /**
     * 校验ip唯一
     * @param businessRouteRequest
     * @return
     */
    private List<BusinessRouteVo> checkIp(BusinessRouteRequest businessRouteRequest){
        BusinessRouteRequest checkIp = new BusinessRouteRequest();
        BeanUtils.copyProperties(businessRouteRequest,checkIp);
        PageInfo<BusinessRouteVo> select = businessRouteDomainService.select(checkIp);
        return select.getList();
    }

    /**
     * 校验数据唯一
     * @param businessRouteRequest
     * @return
     */
    private Result<Integer> checkDataOnly(BusinessRouteRequest businessRouteRequest){
        List<BusinessRouteVo> list = checkIp(businessRouteRequest);
        if(list.size() > NumberUtils.INTEGER_ZERO){
            return new Result<>(false, ErrorMessageContants.IP_REPEAT);
        }
        return null;
    }
}
