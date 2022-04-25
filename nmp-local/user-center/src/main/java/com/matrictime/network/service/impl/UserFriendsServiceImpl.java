package com.matrictime.network.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.jzsg.bussiness.JServiceImpl;
import com.jzsg.bussiness.model.ReqModel;
import com.jzsg.bussiness.model.ResModel;
import com.matrictime.network.api.modelVo.AddUserRequestVo;
import com.alibaba.druid.support.json.JSONUtils;
import com.matrictime.network.api.modelVo.UserFriendVo;
import com.matrictime.network.api.modelVo.UserVo;
import com.matrictime.network.api.request.AddUserRequestReq;
import com.matrictime.network.api.request.UserFriendReq;
import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.api.response.AddUserRequestResp;
import com.matrictime.network.api.response.UserFriendResp;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.base.enums.AddUserRequestEnum;
import com.matrictime.network.controller.WebSocketServer;
import com.matrictime.network.domain.UserFriendsDomainService;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.UserFriendsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class UserFriendsServiceImpl extends SystemBaseService implements UserFriendsService {

    @Resource
    private UserFriendsDomainService userFriendsDomainService;

    @Value("${app.innerUrl}")
    private String url;

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


    @Override
    public Result<AddUserRequestResp> getAddUserInfo(AddUserRequestReq addUserRequestReq) {
        Result result;
        try {
            switch (addUserRequestReq.getDestination()){
                case UcConstants.DESTINATION_OUT:
                    result = commonGetAddUserInfo(addUserRequestReq);
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    addUserRequestReq.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    addUserRequestReq.setUrl(url+UcConstants.URL_GETADDUSERINFO);
                    String param = JSONObject.toJSONString(addUserRequestReq);
                    log.info("非密区向密区发送请求参数param:{}",param);
                    reqModel.setParam(param);
                    ResModel resModel = JServiceImpl.syncSendMsg(reqModel);
                    log.info("非密区接收密区返回值ResModel:{}",JSONObject.toJSONString(resModel));
                    result =(Result) resModel.getReturnValue();
                    break;
                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密
                    result = commonGetAddUserInfo(addUserRequestReq);
                    // 返回值加密
                    break;
                default:
                    throw new SystemException("Destination"+ ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
        }catch (Exception e){
            log.error("UserServiceImpl.verify Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }


    private Result<AddUserRequestResp> commonGetAddUserInfo(AddUserRequestReq addUserRequestReq) {
        Result<AddUserRequestResp> result = new Result<>();
        AddUserRequestResp addUserRequestResp = new AddUserRequestResp();
        try {
            List<AddUserRequestVo> addUserRequestVos = userFriendsDomainService.getAddUserInfo(addUserRequestReq);
            addUserRequestResp.setList(addUserRequestVos);
            result.setResultObj(addUserRequestResp);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

}























