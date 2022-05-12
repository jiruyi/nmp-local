package com.matrictime.network.controller;

import com.matrictime.network.api.request.BindReq;
import com.matrictime.network.api.request.LoginReq;
import com.matrictime.network.api.request.LogoutReq;
import com.matrictime.network.api.request.RegisterReq;
import com.matrictime.network.api.response.LoginResp;
import com.matrictime.network.api.response.RegisterResp;
import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.domain.CommonService;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.LoginService;
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

    @Autowired
    private CommonService commonService;

    /**
     * 登录
     * @return
     */
    @RequestMapping(value = "/login")
    public Result login(@RequestBody LoginReq req){
        try {
            Result<LoginResp> result = loginService.login(req);
            result = commonService.encryptForLogin(req, result);
            return result;
        }catch (Exception e){
            log.error("LoginController.login exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 注册
     * @return
     */
    @RequestMapping(value = "/register")
    public Result register(@RequestBody RegisterReq req){
        try {
            Result<RegisterResp> result = loginService.register(req);
            result = commonService.encrypt(req.getUserId(), req.getDestination(), result);
            return result;
        }catch (Exception e){
            log.error("LoginController.register exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping(value = "/logout")
    public Result logout(@RequestBody LogoutReq req){
        try {
            Result result = loginService.logout(req);
            result = commonService.encrypt(req.getCommonKey(), req.getDestination(), result);
            return result;
        }catch (Exception e){
            log.error("LoginController.logout exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 绑定用户
     */
    @MonitorRequest
    @RequestMapping(value = "/bind")
    public Result bind(@RequestBody BindReq req){
        try {
            Result result = loginService.bind(req);
            result = commonService.encrypt(req.getCommonKey(), req.getDestination(), result);
            return result;
        }catch (Exception e){
            log.error("LoginController.bind exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }
}
