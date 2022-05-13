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
import com.matrictime.network.domain.CommonService;
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
    public Result<Integer> cancelUser(@RequestBody UserRequest userRequest){
        try {
            Result result = userFriendsService.modifyUserInfo(userRequest);
            result = commonService.encrypt(userRequest.getCommonKey(), userRequest.getDestination(), result);
            return  result;
        }catch (Exception e){
            log.error("cancelUser exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    @ApiOperation(value = "查询用户好友列表",notes = "查询用户好友列表")
    @RequestMapping (value = "/selectUserFriend",method = RequestMethod.POST)
    public Result<UserFriendResp> selectUserFriend(@RequestBody UserFriendReq userFriendReq){
        try {
            Result result = userFriendsService.selectUserFriend(userFriendReq);
            result = commonService.encrypt(userFriendReq.getCommonKey(), userFriendReq.getDestination(), result);
            return  result;
        }catch (Exception e){
            log.error("selectUserFriend exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    @ApiOperation(value = "添加好友",notes = "添加好友")
    @RequestMapping (value = "/addFriends",method = RequestMethod.POST)
    public Result<WebSocketVo> addFriends(@RequestBody AddUserRequestReq addUserRequestReq){
        try {
            Result result = userFriendsService.addFriends(addUserRequestReq);
            webSocketSendMsg(addUserRequestReq,result);
            result = commonService.encryptForWs(addUserRequestReq.getCommonKey(), addUserRequestReq.getDestination(), result);
            return result;
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
            result = commonService.encrypt(addUserRequestReq.getCommonKey(), addUserRequestReq.getDestination(), result);
            return result;
        }catch (Exception e){
            log.error("getAddUserInfo exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }

    }

    @ApiOperation(value = "获取好友添加返回信息",notes = "获取好友添加返回信息")
    @RequestMapping (value = "/getRecall",method = RequestMethod.POST)
    public Result<WebSocketVo> getRecall(@RequestBody RecallRequest request){
        Result<WebSocketVo> result = new Result<>();
        try {
            if(request.getAgree() != null && request.getRefuse() == null){
                result = userFriendsService.agreeAddFriedns(request);
                WebSocketServer webSocketServer = WebSocketServer.getWebSocketMap().get(result.getResultObj().getUserId());
                result = commonService.encrypt(request.getCommonKey(), request.getDestination(), result);
                if(webSocketServer != null){
                    webSocketServer.sendMessage(webSocketMessage(result.getResultObj(),"10"));
                }
            }
            if(request.getRefuse() != null && request.getAgree() == null) {
                result = userFriendsService.agreeAddFriedns(request);
                result.setErrorMsg("已经拒绝");
                WebSocketServer webSocketServer = WebSocketServer.getWebSocketMap().get(result.getResultObj().getUserId());
                result = commonService.encrypt(request.getCommonKey(), request.getDestination(), result);
                if(webSocketServer != null){
                    webSocketServer.sendMessage(webSocketMessage(result.getResultObj(),"12"));
                }
            }
            return result;
        }catch (Exception e){
            log.error("getRecall exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }



    /*
        构建WebSocket推送消息
    * */
    private String webSocketMessage(WebSocketVo webSocketVo,String businessCode){
        Map<String,Object> resultMessage = new HashMap<>();
        if(webSocketVo.getDestination().equals("1")){
            resultMessage.put("destination","1");
        }else {
            resultMessage.put("destination","0");
        }
        Map webSocketMap = JSON.parseObject(JSON.toJSONString(webSocketVo),Map.class);
        resultMessage.put("businessCode",businessCode);
        resultMessage.put("result",webSocketMap);
        return JSONUtils.toJSONString(resultMessage);
    }

    private void webSocketSendMsg(AddUserRequestReq addUserRequestReq, Result result){
        WsResultVo wsResultVo = new WsResultVo();
        WsSendVo wsSendVo = null;
        String sendObject = "";
        if(UcConstants.DESTINATION_IN.equals(addUserRequestReq.getDestination())){
            if (result.isSuccess()){
                wsSendVo = JSONObject.parseObject(result.getErrorMsg(), new TypeReference<WsSendVo>() {});
                sendObject = wsSendVo.getSendObject();
                wsSendVo.setSendObject(null);
                wsResultVo.setDestination(UcConstants.DESTINATION_OUT);
                try {
                    String encrypt = commonService.encryptToString(addUserRequestReq.getCommonKey(), addUserRequestReq.getDestination(), wsSendVo);
                    wsResultVo.setResult(encrypt);
                }catch (Exception e){
                    sendObject = "";
                    log.error("webSocketSendMsg exception:{}",e.getMessage());
                }

                result.setErrorMsg(null);


            }
        }
        if (UcConstants.DESTINATION_OUT.equals(addUserRequestReq.getDestination())){
            if (result.isSuccess()){
                wsSendVo = JSONObject.parseObject(result.getErrorMsg(), new TypeReference<WsSendVo>() {});
                sendObject = wsSendVo.getSendObject();
                wsSendVo.setSendObject(null);
                wsResultVo.setDestination(UcConstants.DESTINATION_OUT);
                wsResultVo.setResult(JSONObject.toJSONString(wsSendVo));
                result.setErrorMsg(null);
            }
        }

        if (StringUtils.isNotBlank(sendObject)){
            WebSocketServer webSocketServer = WebSocketServer.getWebSocketMap().get(sendObject);
            if(webSocketServer != null){
                webSocketServer.sendMessage(JSONUtils.toJSONString(wsResultVo));
            }
        }
    }


}

























