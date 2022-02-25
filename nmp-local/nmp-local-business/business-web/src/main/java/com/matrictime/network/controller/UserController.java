package com.matrictime.network.controller;

import com.dianping.cat.Cat;
import com.matrictime.network.base.enums.LoginTypeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.LoginRequest;
import com.matrictime.network.response.LoginResponse;
import com.matrictime.network.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/2/21 0021 17:38
 * @desc
 */
@RequestMapping(value = "/user")
@Api(value = "用户信息相关",tags = "用户信息相关")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
      * @title login
      * @param [loginRequest]
      * @return com.matrictime.network.model.Result<LoginResponse>
      * @description 
      * @author jiruyi
      * @create 2022/2/24 0024 10:13
      */
    @ApiOperation(value = "登录接口",notes = "用户名密码登录方式1 短信验证码2")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        try {

            /**1.0 参数校验**/
            if(ObjectUtils.isEmpty(loginRequest) || ObjectUtils.isEmpty(loginRequest.getType())){
                return new Result(false, ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            /**2.0用户名密码登录**/
            if (loginRequest.getType().equals(LoginTypeEnum.PASSWORD.getCode())){
                if(ObjectUtils.isEmpty(loginRequest.getLoginAccount()) || ObjectUtils.isEmpty(loginRequest.getPassword())) {
                    return new Result(false, ErrorMessageContants.USERNAME_IS_NULL_MSG);
                }
                /**3.0短信验证码登录**/
            }else if(loginRequest.getType().equals(LoginTypeEnum.MOBILEPHONE.getCode())){
                if(ObjectUtils.isEmpty(loginRequest.getPhone()) || ObjectUtils.isEmpty(loginRequest.getSmsCode())) {
                    return new Result(false, ErrorMessageContants.PHONE_CODE_IS_NULL_MSG);
                }
            }else{
                return new Result(false, ErrorMessageContants.LoginType_IS_ERROR_MSG);
            }
            /**4 后端service**/
            return  userService.login(loginRequest);
        }catch (Exception e){
            log.error("login发生异常：{}",e.getMessage());
            Cat.logError(e);
            return new Result<>(false,e.getMessage());
        }
    }

}
