package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.InternetRouteDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.InternetRouteRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.InternetRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2022/9/29.
 */
@Service
@Slf4j
public class InternetRouteServiceImpl implements InternetRouteService {

    @Resource
    private InternetRouteDomainService internetRouteDomainService;

    @Override
    public Result<Integer> insert(InternetRouteRequest internetRouteRequest) {
        Result result = new Result<>();
        try {
            result.setResultObj(internetRouteDomainService.insert(internetRouteRequest));
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("insert:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> delete(InternetRouteRequest internetRouteRequest) {
        Result result = new Result<>();
        try {
            result.setResultObj(internetRouteDomainService.delete(internetRouteRequest));
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("delete:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> update(InternetRouteRequest internetRouteRequest) {
        Result result = new Result<>();
        try {
            result.setResultObj(internetRouteDomainService.update(internetRouteRequest));
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("update:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<PageInfo> select(InternetRouteRequest internetRouteRequest) {
        Result result = new Result<>();
        try {
            result.setResultObj(internetRouteDomainService.select(internetRouteRequest));
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("select:{}",e.getMessage());
        }
        return result;
    }
}
