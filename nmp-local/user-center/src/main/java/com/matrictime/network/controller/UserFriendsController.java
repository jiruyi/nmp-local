package com.matrictime.network.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.matrictime.network.api.request.AddUserRequestReq;
import com.matrictime.network.api.request.RecallRequest;
import com.matrictime.network.api.request.UserFriendReq;
import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.api.response.AddUserRequestResp;
import com.matrictime.network.api.response.RecallResp;
import com.matrictime.network.api.response.UserFriendResp;

import com.matrictime.network.domain.CommonService;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.UserFriendsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/userFriends")
@Api(value = "用户好友",tags = "用户好友接口")
@RestController
@Slf4j
public class UserFriendsController {

    @Resource
    private UserFriendsService userFriendsService;
    @Autowired
    private CommonService commonService;


    @ApiOperation(value = "注销用户",notes = "注销用户")
    @RequestMapping (value = "/cancelUser",method = RequestMethod.POST)
    public Result<Integer> cancelUser(@RequestBody UserRequest userRequest){
        try {
            return  userFriendsService.modifyUserInfo(userRequest);
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
                WebSocketServer webSocketServer = WebSocketServer.getWebSocketMap().get(addUserRequestReq.getAddUserId());
                if(webSocketServer != null){
                    webSocketServer.sendMessage(JSONUtils.toJSONString(messageText(addUserRequestReq)));
                }
                return new Result<>(true,"等待好友验证");
            }
        }catch (Exception e){
            log.error("addFriends exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    @ApiOperation(value = "获取待认证请求信息",notes = "获取待认证请求信息")
    @RequestMapping (value = "/getAddUserInfo",method = RequestMethod.POST)
    public Result<AddUserRequestResp> getAddUserInfo(@RequestBody AddUserRequestReq addUserRequestReq){
        Result<AddUserRequestResp> result;
        try {
            result = userFriendsService.getAddUserInfo(addUserRequestReq);
            return result;
        }catch (Exception e){
            log.error("getAddUserInfo exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }

    }

    @ApiOperation(value = "获取好友添加返回信息",notes = "获取好友添加返回信息")
    @RequestMapping (value = "/getRecall",method = RequestMethod.POST)
    public Result<Integer> getRecall(@RequestBody RecallRequest request){
        Result<Integer> result = new Result();
        RecallResp recallResp = new RecallResp();
        try {
            if(request.getAgree() != null){
                recallResp.setAgree(request.getAgree());
                result = userFriendsService.agreeAddFriedns(request);
                System.out.println(JSONUtils.toJSONString(messageAgreeText(request)));
                WebSocketServer webSocketServer = WebSocketServer.getWebSocketMap().get(request.getUserId());
                if(webSocketServer != null){
                    webSocketServer.sendMessage(JSONUtils.toJSONString(messageAgreeText(request)));
                }
            }
            if(request.getRefuse() != null) {
                result.setErrorMsg("已经拒绝");
                result = userFriendsService.agreeAddFriedns(request);
                WebSocketServer webSocketServer = WebSocketServer.getWebSocketMap().get(request.getUserId());
                if(webSocketServer != null){
                    webSocketServer.sendMessage(JSONUtils.toJSONString(messageRefuseText(request)));
                }
            }
            return result;
        }catch (Exception e){
            return new Result(false,e.getMessage());
        }
    }

    /*
     构建添加好友推送信息json字符串
    */
    private Map messageText(AddUserRequestReq addUserRequestReq){
        Map<String,Object> messageMap = new HashMap<>();
        Map<String,String> dataMap = new HashMap<>();
        if("1".equals(addUserRequestReq.getDestination())){
            messageMap.put("destination","1");
        }else {
            messageMap.put("destination","0");
        }
        messageMap.put("from","uc");
        messageMap.put("businessCode","7");
        dataMap.put("userId",addUserRequestReq.getUserId());
        dataMap.put("addUserId",addUserRequestReq.getAddUserId());
        dataMap.put("sex",addUserRequestReq.getSex());
        dataMap.put("nickName",addUserRequestReq.getNickName());
        messageMap.put("data",dataMap);
        return messageMap;
    }

    /*
     构建同意添加好友推送信息json字符串
   */
    private Map messageAgreeText(RecallRequest recallRequest){
        Map<String,Object> messageMap = new HashMap<>();
        Map<String,String> dataMap = new HashMap<>();
        if("1".equals(recallRequest.getDestination())){
            messageMap.put("destination","1");
        }else {
            messageMap.put("destination","0");
        }
        messageMap.put("from","uc");
        messageMap.put("businessCode","10");
        dataMap.put("userId",recallRequest.getUserId());
        dataMap.put("addUserId",recallRequest.getAddUserId());
        dataMap.put("sex",recallRequest.getSex());
        dataMap.put("nickName",recallRequest.getNickName());
        messageMap.put("data",dataMap);
        return messageMap;
    }

    /*
      构建拒绝添加好友推送信息json字符串
    */
    private Map messageRefuseText(RecallRequest recallRequest){
        Map<String,Object> messageMap = new HashMap<>();
        Map<String,String> dataMap = new HashMap<>();
        if("1".equals(recallRequest.getDestination())){
            messageMap.put("destination","1");
        }else {
            messageMap.put("destination","0");
        }
        messageMap.put("from","uc");
        messageMap.put("businessCode","12");
        dataMap.put("userId",recallRequest.getUserId());
        dataMap.put("addUserId",recallRequest.getAddUserId());
        messageMap.put("data",dataMap);
        return messageMap;
    }



}

























