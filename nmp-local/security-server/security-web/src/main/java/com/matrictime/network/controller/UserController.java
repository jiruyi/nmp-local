package com.matrictime.network.controller;

import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.req.UserReq;
import com.matrictime.network.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
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
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/loginOut",method = RequestMethod.POST)
    public Result loginOut(){
        try {
            Result result = userService.loginOut();
            return result;
        }catch (Exception e){
            log.error("UserController.loginOut exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    @MonitorRequest
    @RequestMapping(value = {"/passwordReset"}, method = {RequestMethod.POST})
    public Result passwordReset(@RequestBody UserReq req) {
        try {
            /**1.0 参数校验**/
            if (ObjectUtils.isEmpty(req) || ObjectUtils.isEmpty(req.getUserId())) {
                return new Result(false, com.matrictime.network.base.exception.ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (ObjectUtils.isEmpty(req.getNewPassword()) || ObjectUtils.isEmpty(req.getConfirmPassword())) {
                return new Result(false, com.matrictime.network.base.exception.ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            //个人信息密码修改
            if (ObjectUtils.isEmpty(req.getOldPassword())) {
                return new Result(false, com.matrictime.network.base.exception.ErrorMessageContants.PARAM_IS_NULL_MSG);
            }

            //密码一致行校验
            if (!req.getNewPassword().equals(req.getConfirmPassword())) {
                return new Result(false, com.matrictime.network.base.exception.ErrorMessageContants.TWO_PASSWORD_ERROR_MSG);
            }
            return userService.passwordReset(req);
        } catch (Exception e) {
            log.error("修改密码异常：{}", e.getMessage());
            return new Result<>(false, e.getMessage());
        }
    }
}
