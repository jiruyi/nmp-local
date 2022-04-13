package com.matrictime.network.controller;

import com.matrictime.network.api.request.LoginReq;
import com.matrictime.network.api.request.LogoutReq;
import com.matrictime.network.api.request.RegisterReq;
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

    /**
     * 登录
     * @return
     */
    @RequestMapping(value = "/login")
    public Result login(LoginReq req){
        try {
            return loginService.login(req);
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
            return loginService.register(req);
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
            return loginService.logout(req);
        }catch (Exception e){
            log.error("LoginController.logout exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }
}
