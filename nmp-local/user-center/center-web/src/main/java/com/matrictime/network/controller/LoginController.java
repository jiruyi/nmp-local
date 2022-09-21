package com.matrictime.network.controller;

import com.jzsg.bussiness.JServiceImpl;
import com.jzsg.bussiness.util.EdException;
import com.matrictime.network.aop.MonitorRequest;
import com.matrictime.network.api.request.*;
import com.matrictime.network.api.response.LoginResp;
import com.matrictime.network.api.response.RegisterResp;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.LoginService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录注册
 */
@RequestMapping(value = "/user")
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/login")
    public Result login(@RequestBody LoginReq req){
        try {
            Result<LoginResp> result = loginService.login(req);
            return result;
        }catch (Exception e){
            log.error("LoginController.login exception:{}",e.getMessage());
            return new Result(false,ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 注册
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/register")
    public Result register(@RequestBody RegisterReq req){
        try {
            Result<RegisterResp> result = loginService.register(req);
            return result;
        }catch (Exception e){
            log.error("LoginController.register exception:{}",e.getMessage());
            return new Result(false,ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 退出
     * @return
     */
    @ApiOperation(value = "用户退出",notes = "用户退出")
    @MonitorRequest
    @RequestMapping(value = "/logout")
    public Result logout(@RequestBody LogoutReq req){
        try {
            Result result = loginService.logout(req);
            return result;
        }catch (Exception e){
            log.error("LoginController.logout exception:{}",e.getMessage());
            return new Result(false,ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 系统登出
     * @param req
     * @return
     */
    @RequestMapping(value = "/syslogout")
    public Result syslogout(@RequestBody LogoutReq req){
        try {
            Result result = loginService.syslogout(req);
            return result;
        }catch (Exception e){
            log.error("LoginController.logout exception:{}",e.getMessage());
            return new Result(false,ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }


    @RequestMapping(value = "/zr")
    public Integer zr(@RequestBody BaseReq req) throws EdException {
        int i = JServiceImpl.setBSAuth(req.getCommonKey(), req.getUuid());
        log.info("接入基站认证结果："+i);
        return i;
    }
}
