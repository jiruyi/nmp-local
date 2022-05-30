package com.matrictime.network.controller;

import com.dianping.cat.Cat;
import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.LoginTypeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.LoginRequest;
import com.matrictime.network.request.UserInfo;
import com.matrictime.network.request.UserRequest;
import com.matrictime.network.response.LoginResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.response.UserInfoResp;
import com.matrictime.network.response.UserListResponse;
import com.matrictime.network.service.UserService;
import com.matrictime.network.shiro.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户信息相关
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/2/21 0021 17:38
 *
 */
@RequestMapping(value = "/user")
@Api(value = "用户信息相关",tags = "用户信息相关")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${token.timeOut}")
    private Integer timeOut;

    /**
     * 登录接口
     * @title login
     * @param [loginRequest]
     * @return com.matrictime.network.model.Result<LoginResponse>
     * @author jiruyi
     * @create 2022/2/24 0024 10:13
     */
    @ApiOperation(value = "登录接口",notes = "用户名密码登录方式1 短信验证码2")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request){
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
            request.getSession().setMaxInactiveInterval(timeOut*3600);
            return  userService.login(loginRequest);
        }catch (Exception e){
            log.error("login发生异常：{}",e.getMessage());
            Cat.logError(e);
            return new Result<>(false,e.getMessage());
        }
    }

    /**
     * @title logout
     * @param [loginRequest]
     * @return com.matrictime.network.model.Result<com.matrictime.network.response.LoginResponse>
     * @description
     * @author jiruyi
     * @create 2022/2/28 0028 10:02
     */
    @ApiOperation(value = "退出系统登录")
    @SystemLog(opermodul = "用户管理模块",operDesc = "用户退出下系统",operType = "登出")
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public Result<LoginResponse> logout(@RequestBody LoginRequest loginRequest){
        try {
            /**1.0 参数校验**/
            if(ObjectUtils.isEmpty(loginRequest) || ObjectUtils.isEmpty(loginRequest.getUserId())){
                return new Result(false, ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            redisTemplate.delete(loginRequest.getUserId()+ DataConstants.USER_LOGIN_JWT_TOKEN);
            log.info("用户:{}退出系统", RequestContext.getUser().getUserId());
            ShiroUtils.logout();
            return new Result(true, ErrorMessageContants.LOGOUT_SUCCESS_MSG);
        }catch (Exception e){
            log.error("用户:{}logout发生异常：{}", RequestContext.getUser().getUserId(),e.getMessage());
            return new Result<>(false,e.getMessage());
        }
    }

    /**
     * @title insertUser
     * @param [userRequest]
     * @return com.matrictime.network.model.Result<java.lang.Integer>
     * @description
     * @author jiruyi
     * @create 2022/2/28 0028 11:06
     */
    @ApiOperation(value = "用户添加")
    @SystemLog(opermodul = "用户管理模块",operDesc = "用户添加",operType = "添加")
    @RequiresPermissions("sys:user:save")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public Result<Integer> insertUser(@RequestBody UserRequest userRequest){
        try {
            /**1.0 参数校验**/
            if(ObjectUtils.isEmpty(userRequest) || ObjectUtils.isEmpty(userRequest.getLoginAccount())
                    || ObjectUtils.isEmpty(userRequest.getPassword())
                    || ObjectUtils.isEmpty(userRequest.getPhoneNumber())
                    || ObjectUtils.isEmpty(userRequest.getRoleId())){
                return new Result(false, ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            //2. 插入
            return  userService.insertUser(userRequest);
        }catch (Exception e){
            log.error("用户:{}插入发生异常：{}", userRequest,e.getMessage());
            return new Result<>(false,e.getMessage());
        }
    }

    /**
     * @title selectUserList
     * @param [userRequest]
     * @return com.matrictime.network.model.Result<com.matrictime.network.response.PageInfo>
     * @description
     * @author jiruyi
     * @create 2022/3/1 0001 9:36
     */
    @ApiOperation(value = "用户查询")
    @SystemLog(opermodul = "用户管理",operDesc = "用户查询",operType = "查询")
    @RequestMapping(value = "/select",method = RequestMethod.POST)
//    @RequiresPermissions("sys:user:query")
    public Result<PageInfo> selectUserList(@RequestBody UserRequest userRequest){
        try {
            return userService.selectUserList(userRequest);
        }catch (Exception e){
            log.error("用户查询发生异常：{}", userRequest,e.getMessage());
            return new Result<>(false,e.getMessage());
        }
    }


    /**
     * @title updateUser
     * @param [userRequest]
     * @return com.matrictime.network.model.Result<java.lang.Integer>
     * @description
     * @author jiruyi
     * @create 2022/2/28 0028 15:22
     */
    @ApiOperation(value = "用户修改")
    @SystemLog(opermodul = "用户管理模块",operDesc = "用户修改",operType = "修改")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @RequiresPermissions("sys:user:update")
    public Result<Integer> updateUser(@RequestBody UserRequest userRequest){
        try {
            /**1.0 参数校验**/
            if(ObjectUtils.isEmpty(userRequest) || ObjectUtils.isEmpty(userRequest.getUserId())
                    || ObjectUtils.isEmpty(userRequest.getPhoneNumber())
                    || ObjectUtils.isEmpty(userRequest.getRoleId())){
                return new Result(false, ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            //2. 编辑
            return  userService.updateUser(userRequest);
        }catch (Exception e){
            log.error("用户:{}插入发生异常：{}", userRequest,e.getMessage());
            return new Result<>(false,e.getMessage());
        }
    }

    /**
     * @title deleteUser
     * @param [userRequest]
     * @return com.matrictime.network.model.Result<java.lang.Integer>
     * @description
     * @author jiruyi
     * @create 2022/2/28 0028 16:29
     */
    @ApiOperation(value = "用户删除")
    @SystemLog(opermodul = "用户管理模块",operDesc = "用户删除",operType = "删除")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @RequiresPermissions("sys:user:delete")
    public Result<Integer> deleteUser(@RequestBody UserRequest userRequest){
        try {
            /**1.0 参数校验**/
            if(ObjectUtils.isEmpty(userRequest) || ObjectUtils.isEmpty(userRequest.getUserId())){
                return new Result(false, ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            //2. 删除
            return  userService.deleteUser(userRequest);
        }catch (Exception e){
            log.error("用户:{}插入发生异常：{}", userRequest,e.getMessage());
            return new Result<>(false,e.getMessage());
        }
    }


    /**
     * @title passwordReset
     * @param [userRequest]
     * @return com.matrictime.network.model.Result<java.lang.Integer>
     * @description
     * @author jiruyi
     * @create 2022/3/1 0001 11:09
     */
    @ApiOperation(value = "密码重置")
    @SystemLog(opermodul = "用户管理模块",operDesc = "密码重置",operType = "重置")
    @RequestMapping(value = "/reset",method = RequestMethod.POST)
    @RequiresPermissions("sys:user:changePasswd")
    public Result<Integer> passwordReset(@RequestBody UserInfo userInfo){
        try {
            /**1.0 参数校验**/
            if(ObjectUtils.isEmpty(userInfo) || ObjectUtils.isEmpty(userInfo.getType())
                    || ObjectUtils.isEmpty(userInfo.getUserId()))
            {
                return new Result(false, ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if( ObjectUtils.isEmpty(userInfo.getNewPassword()) || ObjectUtils.isEmpty(userInfo.getConfirmPassword())){
                return new Result(false, ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            //个人信息密码修改
            if("1".equals(userInfo.getType())){
                if( ObjectUtils.isEmpty(userInfo.getOldPassword())){
                    return new Result(false, ErrorMessageContants.PARAM_IS_NULL_MSG);
                }
            }
            //密码一致行校验
            if(!userInfo.getNewPassword().equals(userInfo.getConfirmPassword())){
                return new Result(false, ErrorMessageContants.TWO_PASSWORD_ERROR_MSG);
            }
            return userService.passwordReset(userInfo);
        }catch (Exception e){
            log.error("用户:{}插入发生异常：{}", userInfo,e.getMessage());
            return new Result<>(false,e.getMessage());
        }
    }




    @ApiOperation(value = "获取单个用户信息",notes = "用户姓名，电话，userId")
    @SystemLog(opermodul = "用户管理模块",operDesc = "查询用户",operType = "查询")
    @RequestMapping (value = "/getUserInfo",method = RequestMethod.POST)
    @RequiresPermissions("sys:user:query")
    public Result<UserInfoResp> selectUserInfo(@RequestBody UserRequest userRequest){
        Result<UserInfoResp> responseResult= new Result();
        try {
            if(ObjectUtils.isEmpty(userRequest.getUserId())){
                userRequest.setUserId(String.valueOf(RequestContext.getUser().getUserId()));
            }
            responseResult =  userService.getUserInfo(userRequest);
        }catch (Exception e){
            responseResult.setErrorMsg(e.getMessage());
            responseResult.setSuccess(false);
        }
        return responseResult;
    }


    /**
     * @title passwordReset
     * @param [userRequest]
     * @return com.matrictime.network.model.Result<java.lang.Integer>
     * @description
     * @author jiruyi
     * @create 2022/3/1 0001 11:09
     */
    @ApiOperation(value = "密码修改")
    @SystemLog(opermodul = "用户管理模块",operDesc = "密码修改",operType = "修改")
    @RequestMapping(value = "/changePasswd",method = RequestMethod.POST)
    public Result<Integer> changePasswd(@RequestBody UserInfo userInfo){
        try {
            /**1.0 参数校验**/
            if(ObjectUtils.isEmpty(userInfo) || ObjectUtils.isEmpty(userInfo.getType())
                    || ObjectUtils.isEmpty(userInfo.getUserId()))
            {
                return new Result(false, ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if( ObjectUtils.isEmpty(userInfo.getNewPassword()) || ObjectUtils.isEmpty(userInfo.getConfirmPassword())){
                return new Result(false, ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            //个人信息密码修改
            if("1".equals(userInfo.getType())){
                if( ObjectUtils.isEmpty(userInfo.getOldPassword())){
                    return new Result(false, ErrorMessageContants.PARAM_IS_NULL_MSG);
                }
            }
            //密码一致行校验
            if(!userInfo.getNewPassword().equals(userInfo.getConfirmPassword())){
                return new Result(false, ErrorMessageContants.TWO_PASSWORD_ERROR_MSG);
            }
            return userService.passwordReset(userInfo);
        }catch (Exception e){
            log.error("用户:{}插入发生异常：{}", userInfo,e.getMessage());
            return new Result<>(false,e.getMessage());
        }
    }

}
