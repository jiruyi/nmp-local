package com.matrictime.network.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.matrictime.network.api.modelVo.WebSocketVo;
import com.matrictime.network.api.modelVo.WsResultVo;
import com.matrictime.network.api.modelVo.WsSendVo;
import com.matrictime.network.api.request.*;
import com.matrictime.network.api.response.AddUserRequestResp;
import com.matrictime.network.api.response.UserFriendResp;

import com.matrictime.network.base.UcConstants;
import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.domain.CommonService;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.UserFriendsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
//    @MonitorRequest
    public Result<Integer> cancelUser(@RequestBody UserRequest userRequest){
        try {
            Result result = userFriendsService.modifyUserInfo(userRequest);
            result = commonService.encrypt(userRequest.getCommonKey(), userRequest.getDestination(), result);
            return  result;
        }catch (Exception e){
            log.error("cancelUser exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    @ApiOperation(value = "查询用户好友列表",notes = "查询用户好友列表")
    @RequestMapping (value = "/selectUserFriend",method = RequestMethod.POST)
//    @MonitorRequest
    public Result<UserFriendResp> selectUserFriend(@RequestBody UserFriendReq userFriendReq){
        try {
            Result result = userFriendsService.selectUserFriend(userFriendReq);
            result = commonService.encrypt(userFriendReq.getCommonKey(), userFriendReq.getDestination(), result);
            return  result;
        }catch (Exception e){
            log.error("selectUserFriend exception:{}",e.getMessage());
            return new Result(false,ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    @ApiOperation(value = "添加好友",notes = "添加好友")
    @RequestMapping (value = "/addFriends",method = RequestMethod.POST)
//    @MonitorRequest
    public Result<WebSocketVo> addFriends(@RequestBody AddUserRequestReq addUserRequestReq){
        try {
            Result result = userFriendsService.addFriends(addUserRequestReq);
            addFriendSendMsg(addUserRequestReq,result);
            result = commonService.encryptForWs(addUserRequestReq.getCommonKey(), addUserRequestReq.getDestination(), result);
            return result;
        }catch (Exception e){
            log.error("addFriends exception:{}",e.getMessage());
            return new Result(false,ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    @ApiOperation(value = "获取待认证请求信息",notes = "获取待认证请求信息")
    @RequestMapping (value = "/getAddUserInfo",method = RequestMethod.POST)
//    @MonitorRequest
    public Result<AddUserRequestResp> getAddUserInfo(@RequestBody AddUserRequestReq addUserRequestReq){
        Result<AddUserRequestResp> result;
        try {
            result = userFriendsService.getAddUserInfo(addUserRequestReq);
            result = commonService.encrypt(addUserRequestReq.getCommonKey(), addUserRequestReq.getDestination(), result);
            return result;
        }catch (Exception e){
            log.error("getAddUserInfo exception:{}",e.getMessage());
            return new Result(false,ErrorMessageContants.SYSTEM_ERROR_MSG);
        }

    }

    @ApiOperation(value = "获取好友添加返回信息",notes = "获取好友添加返回信息")
    @RequestMapping (value = "/getRecall",method = RequestMethod.POST)
//    @MonitorRequest
    public Result getRecall(@RequestBody RecallRequest request){
        Result result = new Result<>();
        try {
            result = userFriendsService.agreeAddFriedns(request);
            agreeFriendSendMsg(request,result);
            result = commonService.encryptForWs(request.getCommonKey(), request.getDestination(), result);
            return result;
        }catch (Exception e){
            log.error("getRecall exception:{}",e.getMessage());
            return new Result(false,ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    private void agreeFriendSendMsg(RecallRequest request,Result result){
        Map<String,WsResultVo> sendMap;
        WsResultVo resultAddUserVo = new WsResultVo();
        WsResultVo resultUserVo = new WsResultVo();
        String sendAddUserObject = "";
        String sendUserObject = "";
        if(StringUtils.isBlank(request.getDestination())){
            if (result.isSuccess()){
                sendMap = JSONObject.parseObject(result.getErrorMsg(), new TypeReference<Map>() {});
                resultAddUserVo = sendMap.get("sendAddUser");
                resultUserVo = sendMap.get("sendUser");
                sendAddUserObject = resultAddUserVo.getSendObject();
                sendUserObject = resultUserVo.getSendObject();
                resultAddUserVo.setSendObject(null);
                resultUserVo.setSendObject(null);
                result.setErrorMsg(null);
            }
        }

        if (StringUtils.isNotBlank(sendAddUserObject)){
            WebSocketServer webSocketServer = WebSocketServer.getWebSocketMap().get(sendAddUserObject);
            if(webSocketServer != null){
                webSocketServer.sendMessage(JSONObject.toJSONString(resultAddUserVo));
            }
        }

        if (StringUtils.isNotBlank(sendUserObject)){
            WebSocketServer webSocketServer = WebSocketServer.getWebSocketMap().get(sendUserObject);
            if(webSocketServer != null){
                webSocketServer.sendMessage(JSONObject.toJSONString(resultUserVo));
            }
        }
    }

    private void addFriendSendMsg(AddUserRequestReq addUserRequestReq,Result result){
        Map<String,WsResultVo> sendMap;
        WsResultVo resultAddUserVo = new WsResultVo();
        WsResultVo resultUserVo = new WsResultVo();
        String sendAddUserObject = "";
        String sendUserObject = "";
        if(StringUtils.isBlank(addUserRequestReq.getDestination())){
            if (result.isSuccess()){
                sendMap = JSONObject.parseObject(result.getErrorMsg(), new TypeReference<Map>() {});
                resultAddUserVo = sendMap.get("sendAddUser");
                resultUserVo = sendMap.get("sendUser");
                sendAddUserObject = resultAddUserVo.getSendObject();
                sendUserObject = resultUserVo.getSendObject();
                resultAddUserVo.setSendObject(null);
                resultUserVo.setSendObject(null);
                result.setErrorMsg(null);
            }
        }

        if (StringUtils.isNotBlank(sendAddUserObject)){
            WebSocketServer webSocketServer = WebSocketServer.getWebSocketMap().get(sendAddUserObject);
            if(webSocketServer != null){
                webSocketServer.sendMessage(JSONObject.toJSONString(resultAddUserVo));
            }
        }

        if (StringUtils.isNotBlank(sendUserObject)){
            WebSocketServer webSocketServer = WebSocketServer.getWebSocketMap().get(sendUserObject);
            if(webSocketServer != null){
                webSocketServer.sendMessage(JSONObject.toJSONString(resultUserVo));
            }
        }
    }


}

























