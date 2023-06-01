package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.SystemHeartbeatDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.SystemHeartbeatVo;
import com.matrictime.network.request.SystemHeartbeatRequest;
import com.matrictime.network.response.SystemHeartbeatResponse;
import com.matrictime.network.service.SystemHeartbeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
@Slf4j
@Service
public class SystemHeartbeatServiceImpl implements SystemHeartbeatService {

    @Resource
    private SystemHeartbeatDomainService systemHeartbeatDomainService;

    /**
     * 更新心跳状态
     * @param systemHeartbeatResponse
     * @return
     */
    @Transactional
    @Override
    public Result<Integer> updateSystemHeartbeat(SystemHeartbeatResponse systemHeartbeatResponse) {
        Result<Integer> result = new Result<>();
        int i = 0;
        List<SystemHeartbeatVo> list = systemHeartbeatResponse.getList();
        for(SystemHeartbeatVo systemHeartbeatVo: list){
            SystemHeartbeatRequest systemHeartbeatRequest = new SystemHeartbeatRequest();
            BeanUtils.copyProperties(systemHeartbeatVo,systemHeartbeatRequest);
            SystemHeartbeatResponse heartbeatResponse = systemHeartbeatDomainService.selectSystemHeartbeat(systemHeartbeatRequest);
            if(CollectionUtils.isEmpty(heartbeatResponse.getList())){
                i = systemHeartbeatDomainService.insertSystemHeartbeat(systemHeartbeatRequest);
            }else {
                i = systemHeartbeatDomainService.updateSystemHeartbeat(systemHeartbeatRequest);
            }
        }
        result.setResultObj(i);
        result.setSuccess(true);
        return result;
    }

    /**
     * 查询心跳状态
     * @param systemHeartbeatRequest
     * @return
     */
    @Override
    public Result<SystemHeartbeatResponse> selectSystemHeartbeat(SystemHeartbeatRequest systemHeartbeatRequest) {
        Result<SystemHeartbeatResponse> result = new Result<>();
        try {
            result.setResultObj(systemHeartbeatDomainService.selectSystemHeartbeat(systemHeartbeatRequest));
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            log.info("selectSystemHeartbeat:{}",e.getMessage());
        }
        return result;
    }
















}
