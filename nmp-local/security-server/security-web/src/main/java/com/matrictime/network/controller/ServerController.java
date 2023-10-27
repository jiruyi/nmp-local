package com.matrictime.network.controller;

import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 启动配置
 * @author hx
 */
@RequestMapping(value = "/server")
@RestController
@Slf4j
public class ServerController {

    @Resource
    private ServerService serverService;

    /**
     * 获取安全服务器的状态
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/getStatus",method = RequestMethod.POST)
    public Result getStatus(){
        try {
            Result result = serverService.getStatus();
            return result;
        }catch (Exception e){
            log.error("ServerController.getStatus exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 启动配置
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/start",method = RequestMethod.POST)
    public Result startServer(){
        try {
            Result result = serverService.start();
            return result;
        }catch (Exception e){
            log.error("ServerController.start exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 获取启动按钮状态
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/getStartStatus",method = RequestMethod.POST)
    public Result getStartStatus(){
        try {
            Result result = serverService.getStartStatus();
            return result;
        }catch (Exception e){
            log.error("ServerController.getStartStatus exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }
}
