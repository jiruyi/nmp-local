package com.matrictime.network.service.impl;


import com.alibaba.druid.support.json.JSONUtils;
import com.matrictime.network.api.modelVo.UserFriendVo;
import com.matrictime.network.api.modelVo.UserVo;
import com.matrictime.network.api.request.AddUserRequestReq;
import com.matrictime.network.api.request.UserFriendReq;
import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.api.response.UserFriendResp;
import com.matrictime.network.base.enums.AddUserRequestEnum;
import com.matrictime.network.controller.WebSocketServer;
import com.matrictime.network.domain.UserFriendsDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.UserFriendsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class UserFriendsServiceImpl implements UserFriendsService {

    @Resource
    private UserFriendsDomainService userFriendsDomainService;

    @Override
    public Result<UserFriendResp> selectUserFriend(UserFriendReq userFriendReq) {
        Result<UserFriendResp> result = new Result<>();
        UserFriendResp userFriendResp = new UserFriendResp();
        try {
            List<UserFriendVo> userFriendVos = userFriendsDomainService.selectUserFriend(userFriendReq);
            userFriendResp.setList(userFriendVos);
            result.setResultObj(userFriendResp);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public Result<Integer> addFriends(AddUserRequestReq addUserRequestReq) {
        Result<Integer> result = new Result<>();
        UserFriendReq userFriendReq = setUserFriendReq(addUserRequestReq);
        UserRequest userRequest = new UserRequest();
        userRequest.setUserId(addUserRequestReq.getAddUserId());
        UserVo userVo = userFriendsDomainService.selectUserInfo(userRequest);
        if(userVo.getAgreeFriend() == 0){
            addUserRequestReq.setStatus(AddUserRequestEnum.AGREE.getCode());
            userFriendsDomainService.addFriends(addUserRequestReq);
            userFriendsDomainService.insertFriend(userFriendReq);
            result.setResultObj(1);
        }else {
            addUserRequestReq.setStatus(AddUserRequestEnum.TOBECERTIFIED.getCode());
            userFriendsDomainService.insertFriend(userFriendReq);
            WebSocketServer webSocketServer = WebSocketServer.getWebSocketMap().get(addUserRequestReq.getAddUserId());
            if(webSocketServer != null){
                webSocketServer.sendMessage(JSONUtils.toJSONString(messageText(addUserRequestReq)));
            }
            result.setResultObj(0);
        }
        return result;
    }

    /*
      构建信息json字符串
    * */
    private Map messageText(AddUserRequestReq addUserRequestReq){
        Map<String,String> messageMap = new HashMap<>();
        messageMap.put("fromUserId",addUserRequestReq.getUserId());
        messageMap.put("toUserId",addUserRequestReq.getAddUserId());
        messageMap.put("message","好友请求");
        messageMap.put("messageCode","100");
        return messageMap;
    }

    private UserFriendReq setUserFriendReq(AddUserRequestReq addUserRequestReq){
        UserFriendReq userFriendReq = new UserFriendReq();
        userFriendReq.setUserId(addUserRequestReq.getUserId());
        userFriendReq.setFriendUserId(addUserRequestReq.getAddUserId());
        userFriendReq.setRemarkName(addUserRequestReq.getRemarkName());
        return userFriendReq;
    }

}























