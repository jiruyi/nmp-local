package com.matrictime.network.controller;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.EditConfigReq;
import com.matrictime.network.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置模块
 * @author hexu
 */
@RestController
@RequestMapping(value = "/config",method = RequestMethod.POST)
@Slf4j
public class ConfigController extends SystemBaseService {

    @Autowired
    private ConfigService configService;

    @RequestMapping (value = "/sync",method = RequestMethod.POST)
    public Result syncConfig(@RequestBody EditConfigReq req){
        try {
            return configService.update(req.getConfigVos());
        }catch (Exception e){
            log.error("ConfigController.sync exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }
}
