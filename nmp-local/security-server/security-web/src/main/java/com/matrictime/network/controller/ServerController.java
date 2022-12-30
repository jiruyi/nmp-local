package com.matrictime.network.controller;

import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/server")
@RestController
@Slf4j
public class ServerController {

    /**
     * 获取安全服务器的状态
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/getStatus",method = RequestMethod.POST)
    public Result getServerStatus(){
        try {
            Result result = new Result<>();
            return result;
        }catch (Exception e){
            log.error("ServerController.getServerStatus exception:{}",e.getMessage());
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
            Result result = new Result<>();
            return result;
        }catch (Exception e){
            log.error("ServerController.startServer exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }
}
