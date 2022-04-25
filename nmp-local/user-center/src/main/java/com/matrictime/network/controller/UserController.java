package com.matrictime.network.controller;

import com.matrictime.network.api.request.ChangePasswdReq;
import com.matrictime.network.api.request.DeleteFriendReq;
import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.api.request.VerifyReq;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
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
 * @date 2022/3/29 0029 10:12
 * @desc
 */
@Api(value = "用户中心",tags = "用户中心相关接口")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户信息修改
     * @title modifyUserInfok
     * @param [userRequest]
     * @return com.matrictime.network.model.Result
     * @description
     * @author jiruyi
     * @create 2022/4/6 0006 9:39
     */
    @ApiOperation(value = "用户信息修改",notes = "用户信息")
    @RequestMapping (value = "/modifyUserInfo",method = RequestMethod.POST)
    public Result modifyUserInfo(@RequestBody UserRequest userRequest){
        try {
            return  userService.modifyUserInfo(userRequest);
        }catch (Exception e){
            log.error("modifyUserInfo exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * @title deleteFriend
     * @param [deleteFriendReq]
     * @return com.matrictime.network.model.Result
     * @description  删除好友
     * @author jiruyi
     * @create 2022/4/8 0008 9:19
     */
    @ApiOperation(value = "删除好友",notes = "删除好友")
    @RequestMapping (value = "/deleteFriend",method = RequestMethod.POST)
    public Result deleteFriend(@RequestBody DeleteFriendReq deleteFriendReq){
        try {
            return  userService.deleteFriend(deleteFriendReq);
        }catch (Exception e){
            log.error("modifyUserInfo exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    @ApiOperation(value = "修改密码",notes = "修改密码")
    @RequestMapping (value = "/changePasswd",method = RequestMethod.POST)
    public Result changePasswd(@RequestBody ChangePasswdReq changePasswdReq){
        try {
            return userService.changePasswd(changePasswdReq);
        }catch (Exception e){
            log.error("changePasswd exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    @ApiOperation(value = "查询单个用户",notes = "查询单个用户")
    @RequestMapping (value = "/queryUserInfo",method = RequestMethod.POST)
    public Result queryUserInfo(@RequestBody UserRequest userRequest){
        try {
            /**1.0 参数校验**/
            return userService.queryUser(userRequest);
        }catch (Exception e){
            log.error("changePasswd exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    @RequestMapping (value = "/verify",method = RequestMethod.POST)
    public Result verify(@RequestBody VerifyReq req){
        try {
            return userService.verify(req);
        }catch (Exception e){
            log.error("UserController.verify exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }


}
