package com.matrictime.network.controller;

import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.ConfigurationValueRequest;
import com.matrictime.network.request.UpdEncryptConfReq;
import com.matrictime.network.service.ConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/10/27.
 */
@RequestMapping(value = "/configuration")
@RestController
@Slf4j
public class ConfigurationController {

    @Resource
    private ConfigurationService configurationService;

    @RequestMapping(value = "/selectConfiguration",method = RequestMethod.POST)
    public Result<Integer> selectConfiguration(@RequestBody ConfigurationValueRequest configurationValueRequest){
        try {
            Result result = configurationService.selectConfiguration(configurationValueRequest);
            return result;
        }catch (Exception e){
            log.error("selectConfiguration exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/insertConfiguration",method = RequestMethod.POST)
    public Result<Integer> insertConfiguration(@RequestBody ConfigurationValueRequest configurationValueRequest){
        try {
            Result result = configurationService.insertConfiguration(configurationValueRequest);
            return result;
        }catch (Exception e){
            log.error("insertConfiguration exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }
}
