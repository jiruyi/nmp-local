package com.matrictime.network.controller;

import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RequestMapping(value = "/common")
@RestController
@Slf4j
public class CommonController {


    @MonitorRequest
    @RequestMapping(value = "/init",method = RequestMethod.POST)
    public Result init(){
        try {
            Result result = new Result<>();

            return result;
        }catch (Exception e){
            log.error("CommonController.init exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }


}
