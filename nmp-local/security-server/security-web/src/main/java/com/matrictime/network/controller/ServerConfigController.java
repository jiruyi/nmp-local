package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.req.ServerConfigRequest;
import com.matrictime.network.service.ServerConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
@RequestMapping(value = "/config")
@RestController
@Slf4j
public class ServerConfigController {

    @Resource
    private ServerConfigService serverConfigService;

    @RequestMapping(value = "/selectServerConfig",method = RequestMethod.POST)
    public Result selectConfiguration(@RequestBody ServerConfigRequest serverConfigRequest){
        try {
            Result result = serverConfigService.selectServerConfig(serverConfigRequest);
            return result;
        }catch (Exception e){
            log.error("selectServerConfig exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/insertServerConfig",method = RequestMethod.POST)
    public Result insertConfiguration(@RequestBody ServerConfigRequest serverConfigRequest){
        try {
            Result result = serverConfigService.insertServerConfig(serverConfigRequest);
            return result;
        }catch (Exception e){
            log.error("insertServerConfig exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }
}
