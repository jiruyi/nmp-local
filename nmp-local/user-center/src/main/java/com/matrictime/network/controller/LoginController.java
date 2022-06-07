package com.matrictime.network.controller;

import com.alibaba.fastjson.JSONObject;
import com.jzsg.bussiness.JServiceImpl;
import com.jzsg.bussiness.util.EdException;
import com.matrictime.network.api.request.*;
import com.matrictime.network.api.response.LoginResp;
import com.matrictime.network.api.response.RegisterResp;
import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.domain.CommonService;
import com.matrictime.network.exception.ErrorMessageContants;
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
//    @MonitorRequest
    @RequestMapping(value = "/login")
    public Result login(@RequestBody LoginReq req){
        try {
            log.info("LoginReq begin:"+ JSONObject.toJSONString(req));
            Result<LoginResp> result = loginService.login(req);
            log.info("LoginReq after:"+ JSONObject.toJSONString(req));
            result = commonService.encryptForLogin(req, result);
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
//    @MonitorRequest
    @RequestMapping(value = "/register")
    public Result register(@RequestBody RegisterReq req){
        try {
            log.info("RegisterReq begin:"+ JSONObject.toJSONString(req));
            Result<RegisterResp> result = loginService.register(req);
            log.info("RegisterReq after:"+ JSONObject.toJSONString(req));
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
//    @MonitorRequest
    @RequestMapping(value = "/logout")
    public Result logout(@RequestBody LogoutReq req){
        try {
            Result result = loginService.logout(req);
            result = commonService.encrypt(req.getCommonKey(), req.getDestination(), result);
            return result;
        }catch (Exception e){
            log.error("LoginController.logout exception:{}",e.getMessage());
            return new Result(false,ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 绑定用户
     */
//    @MonitorRequest
    @RequestMapping(value = "/bind")
    public Result bind(@RequestBody BindReq req){
        try {
            Result result = loginService.bind(req);
            result = commonService.encrypt(req.getCommonKey(), req.getDestination(), result);
            return result;
        }catch (Exception e){
            log.error("LoginController.bind exception:{}",e.getMessage());
            return new Result(false,ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    @RequestMapping(value = "/deleteUser")
    public Result deleteUser(@RequestBody DeleteUserReq req){
        try {
            Result result = loginService.deleteUser(req);
            return result;
        }catch (Exception e){
            log.error("LoginController.deleteUser exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    @RequestMapping(value = "/zr")
    public void zr(@RequestBody BaseReq req) throws EdException {
        int i = JServiceImpl.setBSAuth(req.getCommonKey(), req.getUuid());
        log.info("接入基站认证结果："+i);
    }
}
