package com.matrictime.network.controller;

import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.req.ConfigReq;
import com.matrictime.network.strategy.service.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/config")
@RestController
@Slf4j
public class SecurityConfigController extends SecurityConfig {


    /**
     * 插入/更新配置
     * @param req
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/insertOrUpdate",method = RequestMethod.POST)
    public Result insertOrUpdate(@RequestBody ConfigReq req){
        try {
            Result result =configServiceMap.get(req.getConfigMode()).insertOrUpdate(req);
            return result;
        }catch (Exception e){
            log.error("SecurityConfigController.insertOrUpdate exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 查询配置信息
     * @param req
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public Result query(@RequestBody ConfigReq req){
        try {
            Result result =configServiceMap.get(req.getConfigMode()).query();
            return result;
        }catch (Exception e){
            log.error("SecurityConfigController.query exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 查询网卡信息
     * @param req
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/queryNetworkCardInfo",method = RequestMethod.POST)
    public Result queryNetworkCardInfo(@RequestBody ConfigReq req){
        try {
            Result result =new Result<>();
            return result;
        }catch (Exception e){
            log.error("SecurityConfigController.query exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }



}
