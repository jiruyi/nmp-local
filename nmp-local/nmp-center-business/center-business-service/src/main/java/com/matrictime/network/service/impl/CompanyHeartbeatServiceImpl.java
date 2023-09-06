package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.dao.domain.CompanyHeartbeatDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CompanyHeartbeatVo;
import com.matrictime.network.modelVo.DataPushBody;
import com.matrictime.network.response.CompanyHeartbeatResponse;
import com.matrictime.network.service.CompanyHeartbeatService;
import com.matrictime.network.service.DataHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/18.
 */
@Service("company_heartbeat")
@Slf4j
public class CompanyHeartbeatServiceImpl implements CompanyHeartbeatService, DataHandlerService {

    @Resource
    private CompanyHeartbeatDomainService heartbeatDomainService;

    @Override
    public Result<Integer> insertCompanyHeartbeat(CompanyHeartbeatResponse companyHeartbeatResponse) {
        Result<Integer> result = new Result<>();
        try {
            List<CompanyHeartbeatVo> list = companyHeartbeatResponse.getList();
            result.setResultObj(heartbeatDomainService.insertCompanyHeartbeat(list));
            result.setSuccess(true);
        }catch (Exception e){
            log.info("insertCompanyHeartbeat:{}",e.getMessage());
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * netty接受数据插入数据库
     * @param dataPushBody
     */
    @Override
    public void handlerData(DataPushBody dataPushBody) {
        try {
            if(ObjectUtils.isEmpty(dataPushBody)){
                return;
            }
            String alarmInfoStr = dataPushBody.getBusiDataJsonStr();
            heartbeatDomainService.insertCompanyHeartbeat(JSONObject.parseObject(alarmInfoStr,List.class));
        }catch (Exception e){
            log.error("handlerData exception :{}",e);
        }
    }
}
