package com.matrictime.network.service.impl;

import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.domain.BusinessRouteDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.BusinessRouteRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BusinessRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2022/9/28.
 */
@Service
@Slf4j
public class BusinessRouteServiceImpl implements BusinessRouteService {

    @Resource
    private BusinessRouteDomainService businessRouteDomainService;

    @Override
    public Result<Integer> insert(BusinessRouteRequest businessRouteRequest) {
        Result<Integer> result = new Result<>();
        try {
            result.setResultObj(businessRouteDomainService.insert(businessRouteRequest));
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
            result.setResultObj(businessRouteDomainService.delete(businessRouteRequest));
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
            result.setResultObj(businessRouteDomainService.update(businessRouteRequest));
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
}
