package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.InternetRouteDomainService;
import com.matrictime.network.dao.mapper.NmplInternetRouteMapper;
import com.matrictime.network.dao.model.NmplInternetRoute;
import com.matrictime.network.dao.model.NmplInternetRouteExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.BusinessRouteVo;
import com.matrictime.network.modelVo.InternetRouteVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.BusinessRouteRequest;
import com.matrictime.network.request.InternetRouteRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BusinessRouteService;
import com.matrictime.network.service.InternetRouteService;
import com.matrictime.network.util.CommonCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
 * @date 2022/9/29.
 */
@Service
@Slf4j
public class InternetRouteServiceImpl implements InternetRouteService {

    @Resource
    private InternetRouteDomainService internetRouteDomainService;

    @Resource
    private BusinessRouteService businessRouteService;

    @Resource
    private NmplInternetRouteMapper nmplInternetRouteMapper;

    @Resource
    private AsyncService asyncService;

    @Value("${proxy.port}")
    private String port;

    @Value("${proxy.context-path}")
    private String contextPath;

    @Transactional
    @Override
    public Result<Integer> insert(InternetRouteRequest internetRouteRequest) {
        Result<Integer> result = new Result<>();
        try {
            if(!CommonCheckUtil.isIpv4Legal(internetRouteRequest.getBoundaryStationIp())){
                throw new RuntimeException(IP_FORMAT_ERROR_MSG);
            }
            if(!ObjectUtils.isEmpty(checkDataOnly(internetRouteRequest))){
                return checkDataOnly(internetRouteRequest);
            }
            internetRouteRequest.setRouteId(SnowFlake.nextId_String());
            internetRouteRequest.setUpdateUser(RequestContext.getUser().getCreateUser());
            int insert = internetRouteDomainService.insert(internetRouteRequest);
            if(insert == DataConstants.INSERT_OR_UPDATE_SUCCESS){
                result.setResultObj(insert);
                NmplInternetRouteExample nmplInternetRouteExample = new NmplInternetRouteExample();
                nmplInternetRouteExample.createCriteria().andRouteIdEqualTo(internetRouteRequest.getRouteId());
                List<NmplInternetRoute> nmplInternetRoutes = nmplInternetRouteMapper.selectByExample(nmplInternetRouteExample);
                sendRoute(nmplInternetRoutes.get(NumberUtils.INTEGER_ZERO),DataConstants.INSERT_INTERNET_ROUTE);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("insert:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> delete(InternetRouteRequest internetRouteRequest) {
        Result<Integer> result = new Result<>();
        try {
            internetRouteRequest.setUpdateUser(RequestContext.getUser().getUpdateUser());
            int delete = internetRouteDomainService.delete(internetRouteRequest);
            if(delete == DataConstants.INSERT_OR_UPDATE_SUCCESS){
                result.setResultObj(delete);
                NmplInternetRoute nmplInternetRoute = nmplInternetRouteMapper.selectByPrimaryKey(internetRouteRequest.getId());
                sendRoute(nmplInternetRoute,DataConstants.UPDATE_INTERNET_ROUTE);
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
    public Result<Integer> update(InternetRouteRequest internetRouteRequest) {
        Result<Integer> result = new Result<>();
        try {
            if(!CommonCheckUtil.isIpv4Legal(internetRouteRequest.getBoundaryStationIp())){
                throw new RuntimeException(IP_FORMAT_ERROR_MSG);
            }
            if(!ObjectUtils.isEmpty(checkDataOnly(internetRouteRequest))){
                return checkDataOnly(internetRouteRequest);
            }
            internetRouteRequest.setUpdateUser(RequestContext.getUser().getUpdateUser());
            int update = internetRouteDomainService.update(internetRouteRequest);
            if(update == DataConstants.INSERT_OR_UPDATE_SUCCESS){
                result.setResultObj(update);
                NmplInternetRoute nmplInternetRoute = nmplInternetRouteMapper.selectByPrimaryKey(internetRouteRequest.getId());
                sendRoute(nmplInternetRoute,DataConstants.UPDATE_INTERNET_ROUTE);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("update:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<PageInfo> select(InternetRouteRequest internetRouteRequest) {
        Result<PageInfo> result = new Result<>();
        try {
            result.setResultObj(internetRouteDomainService.select(internetRouteRequest));
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("select:{}",e.getMessage());
        }
        return result;
    }

    //路由推送到各个基站
    private void sendRoute(NmplInternetRoute nmplInternetRoute,String suffix) throws Exception {
        BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
        Result<BaseStationInfoResponse> baseStationInfoResponse = businessRouteService.selectBaseStation(baseStationInfoRequest);
        List<BaseStationInfoVo> baseStationInfoList = baseStationInfoResponse.getResultObj().getBaseStationInfoList();
        //开启多线程
        for (BaseStationInfoVo baseStationInfoVo : baseStationInfoList) {
            Map<String,String> map = new HashMap<>();
            map.put(DataConstants.KEY_DATA, JSONObject.toJSONString(nmplInternetRoute));
            String url = "http://"+baseStationInfoVo.getLanIp()+":"+port+contextPath+suffix;
            map.put(DataConstants.KEY_URL,url);
            map.put(KEY_DEVICE_ID,baseStationInfoVo.getStationId());
            asyncService.httpPush(map);
        }
    }

    /**
     * 校验ip唯一
     * @param internetRouteRequest
     * @return
     */
    private List<NmplInternetRoute> checkIp(InternetRouteRequest internetRouteRequest){
        InternetRouteRequest checkIp = new InternetRouteRequest();
        BeanUtils.copyProperties(internetRouteRequest,checkIp);
        NmplInternetRouteExample nmplInternetRouteExample = new NmplInternetRouteExample();
        nmplInternetRouteExample.createCriteria().andBoundaryStationIpEqualTo(internetRouteRequest.getBoundaryStationIp());
        if(!ObjectUtils.isEmpty(internetRouteRequest.getId())){
            nmplInternetRouteExample.createCriteria().andIdNotEqualTo(internetRouteRequest.getId());
        }
        List<NmplInternetRoute> nmplInternetRoutes = nmplInternetRouteMapper.selectByExample(nmplInternetRouteExample);
        return nmplInternetRoutes;
    }

    /**
     * 校验数据唯一
     * @param internetRouteRequest
     * @return
     */
    private Result<Integer> checkDataOnly(InternetRouteRequest internetRouteRequest){
        List<NmplInternetRoute> list = checkIp(internetRouteRequest);
        if(list.size() > NumberUtils.INTEGER_ZERO){
            return new Result<>(false, ErrorMessageContants.IP_REPEAT);
        }
        return null;
    }
}
