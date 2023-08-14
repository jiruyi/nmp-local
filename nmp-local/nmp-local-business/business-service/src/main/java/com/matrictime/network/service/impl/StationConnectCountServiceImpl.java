package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.StationConnectCountDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.StationConnectCountRequest;
import com.matrictime.network.response.StationConnectCountResponse;
import com.matrictime.network.service.StationConnectCountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/8/8.
 */
@Service
@Slf4j
public class StationConnectCountServiceImpl implements StationConnectCountService {

    @Resource
    private StationConnectCountDomainService stationConnectCountDomainService;

    @Override
    public Result<Integer> selectConnectCount(StationConnectCountRequest stationConnectCountRequest) {
        Result<Integer> result = new Result<>();
        try {
            int i = stationConnectCountDomainService.selectConnectCount(stationConnectCountRequest);
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            log.info("selectConnectCount:{}",e.getMessage());
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<Integer> insertConnectCount(StationConnectCountResponse stationConnectCountResponse) {
        Result<Integer> result = new Result<>();
        try {
            if(CollectionUtils.isEmpty(stationConnectCountResponse.getList())){
                return new Result<>(false,"上传数据为空");
            }
            int i = stationConnectCountDomainService.insertConnectCount(stationConnectCountResponse);
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            log.info("insertConnectCount:{}",e.getMessage());
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
}
