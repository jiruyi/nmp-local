package com.matrictime.network.service.impl;


import com.matrictime.network.dao.domain.CompanyHeartbeatDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CompanyHeartbeatVo;
import com.matrictime.network.response.CompanyHeartbeatResponse;
import com.matrictime.network.service.CompanyHeartbeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/18.
 */
@Service
@Slf4j
public class CompanyHeartbeatServiceImpl implements CompanyHeartbeatService {

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

}
