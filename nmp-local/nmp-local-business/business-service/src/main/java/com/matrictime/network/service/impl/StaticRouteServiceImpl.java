package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.StaticRouteDomainService;
import com.matrictime.network.dao.mapper.NmplStaticRouteMapper;
import com.matrictime.network.dao.model.NmplStaticRoute;
import com.matrictime.network.dao.model.NmplStaticRouteExample;
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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.matrictime.network.base.constant.DataConstants.KEY_DEVICE_ID;
import static com.matrictime.network.base.exception.ErrorMessageContants.IP_FORMAT_ERROR_MSG;
import static com.matrictime.network.constant.DataConstants.IS_EXIST;

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
    private NmplStaticRouteMapper nmplStaticRouteMapper;

    @Resource
    private AsyncService asyncService;

    @Value("${proxy.port}")
    private String port;

    @Value("${proxy.context-path}")
    private String contextPath;

    @Transactional
    @Override
    public Result<Integer> insert(StaticRouteRequest staticRouteRequest) {
        Result<Integer> result = new Result<>();
        try {
            if(!CommonCheckUtil.isIpv4Legal(staticRouteRequest.getServerIp()) &&
                    !CommonCheckUtil.isIpv6Legal(staticRouteRequest.getIpV6())){
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
                NmplStaticRouteExample nmplStaticRouteExample = new NmplStaticRouteExample();
                nmplStaticRouteExample.createCriteria().andRouteIdEqualTo(staticRouteRequest.getRouteId());
                List<NmplStaticRoute> nmplStaticRoutes = nmplStaticRouteMapper.selectByExampleWithBLOBs(nmplStaticRouteExample);
                sendRoute(nmplStaticRoutes.get(NumberUtils.INTEGER_ZERO),DataConstants.INSERT_STATIC_ROUTE);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("系统异常，请稍后重试！");
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
                NmplStaticRoute nmplStaticRoute = nmplStaticRouteMapper.selectByPrimaryKey(staticRouteRequest.getId());
                sendRoute(nmplStaticRoute,DataConstants.UPDATE_STATIC_ROUTE);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("系统异常，请稍后重试！");
            log.info("delete:{}",e.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public Result<Integer> update(StaticRouteRequest staticRouteRequest) {
        Result<Integer> result = new Result<>();
        try {
            if(!CommonCheckUtil.isIpv4Legal(staticRouteRequest.getServerIp()) &&
                    !CommonCheckUtil.isIpv6Legal(staticRouteRequest.getIpV6())){
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
                NmplStaticRoute nmplStaticRoute = nmplStaticRouteMapper.selectByPrimaryKey(staticRouteRequest.getId());
                sendRoute(nmplStaticRoute,DataConstants.UPDATE_STATIC_ROUTE);
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("系统异常，请稍后重试！");
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
            result.setErrorMsg("系统异常，请稍后重试！");
            log.info("select:{}",e.getMessage());
        }
        return result;
    }

    //路由推送到各个基站
    private void sendRoute(NmplStaticRoute nmplStaticRoute,String suffix) throws Exception {
        BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
        Result<BaseStationInfoResponse> baseStationInfoResponse = businessRouteService.selectBaseStation(baseStationInfoRequest);
        List<BaseStationInfoVo> baseStationInfoList = baseStationInfoResponse.getResultObj().getBaseStationInfoList();
        //开启多线程
        for (BaseStationInfoVo baseStationInfoVo : baseStationInfoList) {
            Map<String,String> map = new HashMap<>();
            map.put(DataConstants.KEY_DATA, JSONObject.toJSONString(nmplStaticRoute));
            String url = "http://"+baseStationInfoVo.getLanIp()+":"+port+contextPath+suffix;
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
    private List<NmplStaticRoute> checkStation(StaticRouteRequest staticRouteRequest){
        NmplStaticRouteExample nmplStaticRouteExample = new NmplStaticRouteExample();
        NmplStaticRouteExample.Criteria criteria = nmplStaticRouteExample.createCriteria();
        if(!StringUtils.isEmpty(staticRouteRequest.getNetworkId())){
            criteria.andNetworkIdEqualTo(staticRouteRequest.getNetworkId());
        }
        if(!StringUtils.isEmpty(staticRouteRequest.getStationId())){
            criteria.andStationIdEqualTo(staticRouteRequest.getStationId());
        }
        if(!ObjectUtils.isEmpty(staticRouteRequest.getId())){
            criteria.andIdNotEqualTo(staticRouteRequest.getId());
        }
        criteria.andIsExistEqualTo(IS_EXIST);
        List<NmplStaticRoute> nmplStaticRoutes = nmplStaticRouteMapper.selectByExample(nmplStaticRouteExample);
        return nmplStaticRoutes;
    }

    /**
     * 校验Ip唯一性
     * @param staticRouteRequest
     * @return
     */
    private List<NmplStaticRoute> checkIp(StaticRouteRequest staticRouteRequest){
//        StaticRouteRequest checkSeverIp = new StaticRouteRequest();
//        BeanUtils.copyProperties(staticRouteRequest,checkSeverIp);
        List<NmplStaticRoute> nmplStaticRoutes = new ArrayList<>();
        if(!StringUtils.isEmpty(staticRouteRequest.getServerIp())){
            NmplStaticRouteExample nmplStaticRouteExample = new NmplStaticRouteExample();
            NmplStaticRouteExample.Criteria criteria = nmplStaticRouteExample.createCriteria();
            if(!ObjectUtils.isEmpty(staticRouteRequest.getId())){
                criteria.andIdNotEqualTo(staticRouteRequest.getId());
            }
            criteria.andServerIpEqualTo(staticRouteRequest.getServerIp());
            criteria.andIsExistEqualTo(IS_EXIST);
            nmplStaticRoutes = nmplStaticRouteMapper.selectByExample(nmplStaticRouteExample);
        }
        if(!StringUtils.isEmpty(staticRouteRequest.getIpV6())){
            NmplStaticRouteExample nmplStaticRouteExample1= new NmplStaticRouteExample();
            NmplStaticRouteExample.Criteria criteria1 = nmplStaticRouteExample1.createCriteria();
            if(!ObjectUtils.isEmpty(staticRouteRequest.getId())){
                criteria1.andIdNotEqualTo(staticRouteRequest.getId());
            }
            criteria1.andIpV6EqualTo(staticRouteRequest.getIpV6());
            criteria1.andIsExistEqualTo(IS_EXIST);
            List<NmplStaticRoute> nmplStaticRoutes1 = nmplStaticRouteMapper.selectByExample(nmplStaticRouteExample1);
            nmplStaticRoutes.addAll(nmplStaticRoutes1);
        }

        return nmplStaticRoutes;
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
