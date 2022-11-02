package com.matrictime.network.controller.back;

import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.UserReq;
import com.matrictime.network.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping(value = "/user")
@RestController
@Slf4j
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 用户登录
     * @param req
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody UserReq req){
        try {
            Result result = userService.login(req);
            return result;
        }catch (Exception e){
            log.error("UserController.login exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 用户登出
     * @param req
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/loginOut",method = RequestMethod.POST)
    public Result loginOut(@RequestBody UserReq req){
        try {
            Result result = userService.loginOut(req);
            return result;
        }catch (Exception e){
            log.error("UserController.loginOut exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

}
