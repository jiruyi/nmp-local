package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.SystemHeartbeatDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.SystemHeartbeatRequest;
import com.matrictime.network.response.SystemHeartbeatResponse;
import com.matrictime.network.service.SystemHeartbeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
@Slf4j
@Service
public class SystemHeartbeatServiceImpl implements SystemHeartbeatService {

    @Resource
    private SystemHeartbeatDomainService systemHeartbeatDomainService;

    @Override
    public Result<Integer> updateSystemHeartbeat(SystemHeartbeatRequest systemHeartbeatRequest) {
        Result<Integer> result = new Result<>();
        int i = 0;
        try {
            SystemHeartbeatResponse systemHeartbeatResponse =
                    systemHeartbeatDomainService.selectSystemHeartbeat(systemHeartbeatRequest);
            if(CollectionUtils.isEmpty(systemHeartbeatResponse.getList())){
                i = systemHeartbeatDomainService.insertSystemHeartbeat(systemHeartbeatRequest);
            }else {
                i = systemHeartbeatDomainService.updateSystemHeartbeat(systemHeartbeatRequest);
            }
            result.setResultObj(i);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg("");
            result.setSuccess(false);
            log.info("updateSystemHeartbeat:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<SystemHeartbeatResponse> selectSystemHeartbeat(SystemHeartbeatRequest systemHeartbeatRequest) {
        Result<SystemHeartbeatResponse> result = new Result<>();
        try {
            result.setResultObj(systemHeartbeatDomainService.selectSystemHeartbeat(systemHeartbeatRequest));
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("");
            log.info("selectSystemHeartbeat:{}",e.getMessage());
        }
        return result;
    }
















}
