package com.matrictime.network.controller;


import com.matrictime.network.api.request.AddUserRequestReq;
import com.matrictime.network.api.request.UserFriendReq;
import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.api.response.UserFriendResp;

import com.matrictime.network.model.Result;
import com.matrictime.network.service.UserFriendsService;
import com.matrictime.network.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping(value = "/userFriends")
@Api(value = "用户好友",tags = "用户好友接口")
@RestController
@Slf4j
public class UserFriendsController {

    @Resource
    private UserService userService;

    @Resource
    private UserFriendsService userFriendsService;


    @ApiOperation(value = "注销用户",notes = "注销用户")
    @RequestMapping (value = "/cancelUser",method = RequestMethod.POST)
    public Result<Integer> createGroup(@RequestBody UserRequest userRequest){
        try {
            return  userService.modifyUserInfo(userRequest);
        }catch (Exception e){
            log.error("cancelUser exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    @ApiOperation(value = "查询用户好友列表",notes = "查询用户好友列表")
    @RequestMapping (value = "/selectUserFriend",method = RequestMethod.POST)
    public Result<UserFriendResp> selectUserFriend(@RequestBody UserFriendReq userFriendReq){
        try {
            return userFriendsService.selectUserFriend(userFriendReq);
        }catch (Exception e){
            log.error("selectUserFriend exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    @ApiOperation(value = "添加好友",notes = "添加好友")
    @RequestMapping (value = "/addFriends",method = RequestMethod.POST)
    public Result<Integer> addFriends(@RequestBody AddUserRequestReq addUserRequestReq){
        Result<Integer> result;
        try {
            result = userFriendsService.addFriends(addUserRequestReq);
            if(result.getResultObj() == 1){
                return result;
            }else {
                return new Result<>(true,"等待好友验证");
            }
        }catch (Exception e){
            log.error("addFriends exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

}

























