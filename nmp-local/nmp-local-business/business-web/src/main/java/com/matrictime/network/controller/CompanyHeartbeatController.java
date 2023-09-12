package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.response.CompanyHeartbeatResponse;
import com.matrictime.network.service.CompanyHeartbeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/8/18.
 */
@RequestMapping(value = "/companyHeartbeat")
@RestController
@Slf4j
public class CompanyHeartbeatController {

    @Resource
    private CompanyHeartbeatService heartbeatService;

    @RequestMapping(value = "/insertCompanyHeartbeat")
    public Result<Integer> insertCompanyHeartbeat(@RequestBody CompanyHeartbeatResponse companyHeartbeatResponse){
        Result<Integer> result = new Result<>();
        try {
            if(ObjectUtils.isEmpty(companyHeartbeatResponse)){
                return new Result<>(false,"上传参数为空");
            }
            if(CollectionUtils.isEmpty(companyHeartbeatResponse.getList())){
                return new Result<>(false,"上传参数为空");
            }
            result = heartbeatService.insertCompanyHeartbeat(companyHeartbeatResponse);
        }catch (Exception e){
            log.info("insertCompanyHeartbeat:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }
}
