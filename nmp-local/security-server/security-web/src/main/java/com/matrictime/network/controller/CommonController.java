package com.matrictime.network.controller;

import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.req.InitReq;
import com.matrictime.network.resp.InitResp;
import com.matrictime.network.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 通用接口模块
 */
@RequestMapping(value = "/common")
@RestController
@Slf4j
public class CommonController {

    @Autowired
    private CommonService commonService;

    /**
     * 获取代理端安全服务器相关信息
     * @param req
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/init",method = RequestMethod.POST)
    public Result<InitResp> init(@RequestBody InitReq req){
        try {
            Result<InitResp> result = commonService.init(req);
            return result;
        }catch (Exception e){
            log.error("CommonController.init exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }


}
