package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.ServerConfigVo;
import com.matrictime.network.service.ServerConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/11/6.
 */
@RestController
@RequestMapping(value = "/serverConfig",method = RequestMethod.POST)
@Slf4j
public class ServerConfigController {

    @Resource
    private ServerConfigService serverConfigService;

    @RequestMapping(value = "/insertServerConfig",method = RequestMethod.POST)
    public Result insertCaManage(@RequestBody ServerConfigVo serverConfigVo){
        try {
            Result result = serverConfigService.insertConfig(serverConfigVo);
            return result;
        }catch (Exception e){
            log.error("insertServerConfig exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

}
