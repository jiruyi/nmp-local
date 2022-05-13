package com.matrictime.network.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jzsg.bussiness.JServiceImpl;
import com.jzsg.bussiness.model.ReqModel;
import com.jzsg.bussiness.model.ResModel;
import com.matrictime.network.api.modelVo.*;
import com.matrictime.network.api.request.*;
import com.matrictime.network.api.response.AddUserRequestResp;
import com.matrictime.network.api.response.UserFriendResp;
import com.matrictime.network.api.response.UserResp;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.base.enums.AddUserRequestEnum;
import com.matrictime.network.base.util.ReqUtil;
import com.matrictime.network.domain.UserDomainService;
import com.matrictime.network.domain.UserFriendsDomainService;
import com.matrictime.network.domain.UserGroupDomianService;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.UserFriendsService;
import com.matrictime.network.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UserFriendsServiceImpl extends SystemBaseService implements UserFriendsService {

    @Resource
    private UserFriendsDomainService userFriendsDomainService;

    @Resource
    private UserDomainService userDomainService;

    @Resource
    private UserGroupDomianService userGroupDomianService;

    @Value("${app.innerUrl}")
    private String url;

    @Override
    public Result<UserFriendResp> selectUserFriend(UserFriendReq userFriendReq) {
        Result<UserFriendResp> result = new Result<>();
        try {
            ReqUtil jsonUtil = new ReqUtil<>(userFriendReq);
            userFriendReq = (UserFriendReq) jsonUtil.jsonReqToDto(userFriendReq);
            switch (userFriendReq.getDestination()){
                case UcConstants.DESTINATION_OUT:
                    result = commonSelectUserFriend(userFriendReq);
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    userFriendReq.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    userFriendReq.setUrl(url+UcConstants.URL_SELECT_USER_FRIEND);
                    String param = JSONObject.toJSONString(userFriendReq);
                    log.info("非密区向密区发送请求参数param:{}",param);
                    reqModel.setParam(param);
                    ResModel resModel = JServiceImpl.syncSendMsg(reqModel);
                    log.info("非密区接收密区返回值ResModel:{}",JSONObject.toJSONString(resModel));
                    Object returnValueM = resModel.getReturnValue();
                    if(returnValueM != null && returnValueM instanceof String){
                        ResModel syncResModel = JSONObject.parseObject((String) returnValueM, ResModel.class);
                        result = JSONObject.parseObject(syncResModel.getReturnValue().toString(), Result.class);
                    }else {
                        throw new SystemException("UserFriendsServiceImpl.selectUserFriend"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                    break;
                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密
                    ReqUtil reqUtil = new ReqUtil<>(userFriendReq);
                    userFriendReq = (UserFriendReq)reqUtil.decryJsonToReq(userFriendReq);
                    result =  commonSelectUserFriend(userFriendReq);
                    break;
                default:
                    throw new SystemException("Destination"+ ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    //查询好友列表逻辑请求
    private Result<UserFriendResp> commonSelectUserFriend(UserFriendReq userFriendReq) {
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

    @Override
    public Result addFriends(AddUserRequestReq addUserRequestReq) {
        Result result = new Result<>();
        ReqUtil jsonUtil = new ReqUtil<>(addUserRequestReq);
        addUserRequestReq = (AddUserRequestReq) jsonUtil.jsonReqToDto(addUserRequestReq);
        UserFriendReq userFriendReq = setUserFriendReq(addUserRequestReq);
        UserRequest userRequest = new UserRequest();
        userRequest.setUserId(addUserRequestReq.getAddUserId());
        try {
            switch (addUserRequestReq.getDestination()){
                case UcConstants.DESTINATION_OUT:
                    result = commonAddFriends(userFriendReq,addUserRequestReq,userRequest);
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    addUserRequestReq.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    addUserRequestReq.setUrl(url+UcConstants.URL_ADD_FRIENDS);
                    String param = JSONObject.toJSONString(addUserRequestReq);
                    log.info("非密区向密区发送请求参数param:{}",param);
                    reqModel.setParam(param);
                    ResModel resModel = JServiceImpl.syncSendMsg(reqModel);
                    log.info("非密区接收密区返回值ResModel:{}",JSONObject.toJSONString(resModel));
                    Object returnValueM = resModel.getReturnValue();
                    if(returnValueM != null && returnValueM instanceof String){
                        ResModel syncResModel = JSONObject.parseObject((String) returnValueM, ResModel.class);
                        result = JSONObject.parseObject(syncResModel.getReturnValue().toString(), Result.class);
                    }else {
                        throw new SystemException("UserFriendsServiceImpl.addFriends"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                    break;
                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密
                    ReqUtil reqUtil = new ReqUtil<>(addUserRequestReq);
                    addUserRequestReq = (AddUserRequestReq)reqUtil.decryJsonToReq(addUserRequestReq);
                    UserFriendReq friendReq = setUserFriendReq(addUserRequestReq);
                    UserRequest request = new UserRequest();
                    request.setUserId(addUserRequestReq.getAddUserId());
                    result = commonAddFriends(friendReq,addUserRequestReq,request);
                    break;
                default:
                    throw new SystemException("Destination"+ ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<WebSocketVo> agreeAddFriedns(RecallRequest recallRequest) {
        Result<WebSocketVo> result = new Result<>();
        ReqUtil jsonUtil = new ReqUtil<>(recallRequest);
        recallRequest = (RecallRequest) jsonUtil.jsonReqToDto(recallRequest);
        AddUserRequestReq addUserRequestReq = setAddUserRequestReq(recallRequest);
        UserFriendReq userFriendReq = setUserFriendReq(addUserRequestReq);
        UserRequest userRequest = new UserRequest();
        userRequest.setUserId(addUserRequestReq.getAddUserId());
        try {
            switch (recallRequest.getDestination()){
                case UcConstants.DESTINATION_OUT:
                    result = commonAddFriends(userFriendReq,addUserRequestReq,userRequest);
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    recallRequest.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    recallRequest.setUrl(url+UcConstants.URL_ADD_FRIENDS);
                    String param = JSONObject.toJSONString(recallRequest);
                    log.info("非密区向密区发送请求参数param:{}",param);
                    reqModel.setParam(param);
                    ResModel resModel = JServiceImpl.syncSendMsg(reqModel);
                    log.info("非密区接收密区返回值ResModel:{}",JSONObject.toJSONString(resModel));
                    Object returnValueM = resModel.getReturnValue();
                    if(returnValueM != null && returnValueM instanceof String){
                        ResModel syncResModel = JSONObject.parseObject((String) returnValueM, ResModel.class);
                        result = JSONObject.parseObject(syncResModel.getReturnValue().toString(), Result.class);
                        //Result<Integer> returnRes = JSONObject.parseObject(syncResModel.getReturnValue().toString(),new TypeReference<Result<Integer>>(){});
                    }else {
                        throw new SystemException("UserFriendsServiceImpl.addFriends"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                    break;
                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密
                    ReqUtil reqUtil = new ReqUtil<>(recallRequest);
                    recallRequest = (RecallRequest)reqUtil.decryJsonToReq(recallRequest);
                    AddUserRequestReq setAddUserRequestReq = setAddUserRequestReq(recallRequest);
                    UserFriendReq friendReq = setUserFriendReq(addUserRequestReq);
                    UserRequest request = new UserRequest();
                    userRequest.setUserId(addUserRequestReq.getAddUserId());
                    result = commonAddFriends(friendReq,setAddUserRequestReq,request);
                    break;
                default:
                    throw new SystemException("Destination"+ ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    //添加好友信息逻辑
    private Result commonAddFriends(UserFriendReq userFriendReq,AddUserRequestReq addUserRequestReq,
                                             UserRequest userRequest) {
        Result<WebSocketVo> result = new Result<>();
        UserRequest request = new UserRequest();
        String userId = "";
        request.setUserId(addUserRequestReq.getUserId());
        UserVo userVo = userFriendsDomainService.selectUserInfo(userRequest);
        AddRequestVo addRequestVo = userFriendsDomainService.selectGroupId(addUserRequestReq);
        UserVo user = userFriendsDomainService.selectUserInfo(request);
        if(userVo.getAgreeFriend() == 0 || addUserRequestReq.getAgree() != null){
            WsSendVo wsSendVo = new WsSendVo();
            if(addUserRequestReq.getAgree() == null && userVo.getAgreeFriend() == 0){
                if(!AddRequestStatusFlag(addRequestVo).isSuccess()){
                    return AddRequestStatusFlag(addRequestVo);
                }
                userId = addUserRequestReq.getAddUserId();

                addUserRequestReq.setStatus(AddUserRequestEnum.AGREE.getCode());
                addUserRequestReq.setRequestId(SnowFlake.nextId_String());
                userFriendsDomainService.addFriends(addUserRequestReq);
            }
            if(addUserRequestReq.getAgree() != null && userVo.getAgreeFriend() == 1){
                userId = addUserRequestReq.getUserId();
                userFriendsDomainService.update(addUserRequestReq);
            }
            //判断user_friend表中是否有该好友
            List<UserFriendVo> userFriendVos = userFriendsDomainService.selectUserFriend(userFriendReq);
            if(CollectionUtils.isEmpty(userFriendVos)){
                userFriendsDomainService.insertFriend(userFriendReq);
            }
            //直接添加好友并把好友分组到该分组中
            if(addUserRequestReq.getGroupId() != null){
                setFriendGroup(addUserRequestReq);
            }
            //同意添加好友把好友进行分组
            if(addRequestVo != null){
                agreeSetFriendGroup(addRequestVo,addUserRequestReq);
            }
            //被添加好友把好友添加到组
            setAgreeAddFriendGroup(addUserRequestReq);
            //查找被添加用户的默认组
            GroupReq groupReq = new GroupReq();
            groupReq.setOwner(addUserRequestReq.getAddUserId());
            GroupVo groupVo = userFriendsDomainService.selectGroupInfo(groupReq);
            //添加到默认分组
            setAddFriendGroup(groupVo,addUserRequestReq);
            WebSocketVo webSocketVo = setWebSocketVo(addUserRequestReq,user);

            wsSendVo.setData(webSocketVo);
            wsSendVo.setSendObject(userId);
            buildResult(1,null,JSONObject.toJSONString(wsSendVo));
        }
        //拒绝添加好友
        if(userVo.getAgreeFriend() == 1 || addUserRequestReq.getRefuse() != null) {
            WsSendVo wsSendVo = new WsSendVo();
            WebSocketVo webSocketVo = new WebSocketVo();
            if(userVo.getAgreeFriend() == 1 && addUserRequestReq.getRefuse() == null
                    && addUserRequestReq.getAgree() == null){
                addUserRequestReq.setStatus(AddUserRequestEnum.TOBECERTIFIED.getCode());
                addUserRequestReq.setRequestId(SnowFlake.nextId_String());
                webSocketVo = setWebSocketVo(addUserRequestReq,user);
                wsSendVo.setData(webSocketVo);
                wsSendVo.setSendObject(userId);
                //等待好友确认添加
                userFriendsDomainService.addFriends(addUserRequestReq);
                buildResult(2,null,JSONObject.toJSONString(wsSendVo));
            }
            if(userVo.getAgreeFriend() == 1 && addUserRequestReq.getRefuse() != null
                    && addUserRequestReq.getAgree() == null){
                webSocketVo.setUserId(addUserRequestReq.getUserId());
                webSocketVo.setRequestId(addUserRequestReq.getRequestId());
                //拒绝添加
                wsSendVo.setData(webSocketVo);
                wsSendVo.setSendObject(userId);
                userFriendsDomainService.update(addUserRequestReq);
                buildResult(3,null,JSONObject.toJSONString(wsSendVo));
            }
            result.setResultObj(webSocketVo);

        }
        return result;
    }

    //构建推送返回体
    private WebSocketVo setWebSocketVo(AddUserRequestReq addUserRequestReq,UserVo user){
        WebSocketVo webSocketVo = new WebSocketVo();
        webSocketVo.setAddUserId(addUserRequestReq.getAddUserId());
        webSocketVo.setUserId(addUserRequestReq.getUserId());
        webSocketVo.setNickName(user.getNickName());
        webSocketVo.setPhoneNumber(user.getPhoneNumber());
        webSocketVo.setSex(user.getSex());
        webSocketVo.setRequestId(addUserRequestReq.getRequestId());
        webSocketVo.setDestination(addUserRequestReq.getDestination());
        return webSocketVo;
    }

    //直接添加好友并把好友分组到该分组中
    private void setFriendGroup(AddUserRequestReq addUserRequestReq){
        String groupIdArray = addUserRequestReq.getGroupId();
        String[] groupId = groupIdArray.split(",");
        for (int groupIdIndex = 0;groupIdIndex < groupId.length;groupIdIndex++){
            UserGroupReq userGroupReq = new UserGroupReq();
            userGroupReq.setUserId(addUserRequestReq.getAddUserId());
            userGroupReq.setGroupId(groupId[groupIdIndex]);
            //判断user_group是否有该数据
            List<UserGroupVo> userGroupVos = userGroupDomianService.queryUserGroup(userGroupReq);
            if(CollectionUtils.isEmpty(userGroupVos)){
                userGroupDomianService.createUserGroup(userGroupReq);
            }
        }
    }

    private void agreeSetFriendGroup(AddRequestVo addRequestVo,AddUserRequestReq addUserRequestReq){
        if(addRequestVo.getGroupId() != null){
            String groupIdArray = addRequestVo.getGroupId();
            String[] groupId = groupIdArray.split(",");
            for (int groupIdIndex = 0;groupIdIndex < groupId.length;groupIdIndex++){
                UserGroupReq userGroupReq = new UserGroupReq();
                userGroupReq.setUserId(addUserRequestReq.getAddUserId());
                userGroupReq.setGroupId(groupId[groupIdIndex]);
                //判断user_group是否有该数据
                List<UserGroupVo> userGroupVos = userGroupDomianService.queryUserGroup(userGroupReq);
                if(CollectionUtils.isEmpty(userGroupVos)){
                    userGroupDomianService.createUserGroup(userGroupReq);
                }
            }
        }
    }

    //被添加好友把好友添加到组
    private void setAgreeAddFriendGroup(AddUserRequestReq addUserRequestReq){
        if(addUserRequestReq.getAddGroupId() != null){
            String addGroupIdArray = addUserRequestReq.getAddGroupId();
            String[] groupId = addGroupIdArray.split(",");
            for (int addGroupIdIndex = 0;addGroupIdIndex < groupId.length;addGroupIdIndex++){
                UserGroupReq req = new UserGroupReq();
                req.setUserId(addUserRequestReq.getUserId());
                req.setGroupId(groupId[addGroupIdIndex]);
                //判断user_group是否有该数据
                List<UserGroupVo> userGroupVos = userGroupDomianService.queryUserGroup(req);
                if(CollectionUtils.isEmpty(userGroupVos)){
                    userGroupDomianService.createUserGroup(req);
                }
            }
        }
    }

    //查找被添加用户的默认组
    private void setAddFriendGroup(GroupVo groupVo,AddUserRequestReq addUserRequestReq){
        UserFriendReq friendReq = new UserFriendReq();
        friendReq.setUserId(addUserRequestReq.getAddUserId());
        friendReq.setFriendUserId(addUserRequestReq.getUserId());
        List<UserFriendVo> userFriendVo = userFriendsDomainService.selectUserFriend(friendReq);
        if(groupVo != null && addUserRequestReq.getAddGroupId() == null){
            UserGroupReq req = new UserGroupReq();
            req.setGroupId(groupVo.getGroupId().toString());
            req.setUserId(addUserRequestReq.getUserId());
            List<UserGroupVo> friendUserGroupVos = userGroupDomianService.queryUserGroup(req);
            if(CollectionUtils.isEmpty(friendUserGroupVos)){
                userGroupDomianService.createUserGroup(req);
                if(CollectionUtils.isEmpty(userFriendVo)){
                    userFriendsDomainService.insertFriend(friendReq);
                }
            }
        }else {
            if(CollectionUtils.isEmpty(userFriendVo)){
                userFriendsDomainService.insertFriend(friendReq);
            }
        }
    }

    //判断是否已经添同意添加好友
    private Result AddRequestStatusFlag(AddRequestVo addRequestVo){
        Result result = new Result<>();
        if(addRequestVo != null){
            if("2".equals(addRequestVo.getStatus())){
                result.setErrorMsg("该用户已经被添加");
                result.setSuccess(false);
            }
        }
        return result;
    }


    @Override
    public Result<AddUserRequestResp> getAddUserInfo(AddUserRequestReq addUserRequestReq) {
        ReqUtil<AddUserRequestReq> jsonUtil = new ReqUtil<>(addUserRequestReq);
        addUserRequestReq = jsonUtil.jsonReqToDto(addUserRequestReq);
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
                    Object returnValue = resModel.getReturnValue();
                    if(returnValue != null && returnValue instanceof String){
                        ResModel syncResModel = JSONObject.parseObject((String) returnValue, ResModel.class);
                        result = JSONObject.parseObject(syncResModel.getReturnValue().toString(), Result.class);
                        //result = JSONObject.parseObject((String) returnValue, Result.class);
                    }else {
                        throw new SystemException("modifyUserGroup"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                    break;
                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密
                    ReqUtil<AddUserRequestReq> reqUtil = new ReqUtil<>(addUserRequestReq);
                    AddUserRequestReq addUserRequestReq1 = reqUtil.decryJsonToReq(addUserRequestReq);
                    result = commonGetAddUserInfo(addUserRequestReq1);
                    // 返回值加密
                    break;
                default:
                    throw new SystemException("Destination"+ ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
        }catch (Exception e){
            log.error("getAddUserInfo Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result modifyUserInfo(UserRequest userRequest) {
        Result result = new Result();
        try {
            ReqUtil jsonUtil = new ReqUtil<>(userRequest);
            userRequest = (UserRequest) jsonUtil.jsonReqToDto(userRequest);
            switch (userRequest.getDestination()){
                case UcConstants.DESTINATION_OUT:
                    result.setResultObj(commonCancelUser(userRequest));
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    userRequest.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    userRequest.setUrl(url+UcConstants.URL_CANCEL_USER);
                    String param = JSONObject.toJSONString(userRequest);
                    reqModel.setParam(param);
                    ResModel resModel = JServiceImpl.syncSendMsg(reqModel);
                    log.info("非密区接收密区返回值ResModel:{}",JSONObject.toJSONString(resModel));
                    Object returnValueM = resModel.getReturnValue();
                    if(returnValueM != null && returnValueM instanceof String){
                        ResModel syncResModel = JSONObject.parseObject((String) returnValueM, ResModel.class);
                        result = JSONObject.parseObject(syncResModel.getReturnValue().toString(), Result.class);
                    }else {
                        throw new SystemException("UserFriendsServiceImpl.modifyUserInfo"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                    break;
                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密
                    ReqUtil reqUtil = new ReqUtil<>(userRequest);
                    userRequest = (UserRequest)reqUtil.decryJsonToReq(userRequest);
                    result.setResultObj(commonCancelUser(userRequest));
                    break;
                default:
                    throw new SystemException("Destination"+ ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
            return result;
        }catch (Exception e){
            log.error("modifyUserInfo exception:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            return result;
        }
    }

    //用户注销代码逻辑
    private int commonCancelUser(UserRequest userRequest){
        int n = userDomainService.modifyUserInfo(userRequest);
        return n;
    }


    //构建添加好友信息请求体
    private UserFriendReq setUserFriendReq(AddUserRequestReq addUserRequestReq){
        UserFriendReq userFriendReq = new UserFriendReq();
        userFriendReq.setUserId(addUserRequestReq.getUserId());
        userFriendReq.setFriendUserId(addUserRequestReq.getAddUserId());
        userFriendReq.setRemarkName(addUserRequestReq.getRemarkName());
        return userFriendReq;
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

    private AddUserRequestReq setAddUserRequestReq(RecallRequest recallRequest){
        AddUserRequestReq addUserRequestReq = new AddUserRequestReq();
        addUserRequestReq.setUserId(recallRequest.getUserId());
        addUserRequestReq.setAddUserId(recallRequest.getAddUserId());
        addUserRequestReq.setRemarkName(recallRequest.getRemarkName());
        addUserRequestReq.setAgree(recallRequest.getAgree());
        addUserRequestReq.setRefuse(recallRequest.getRefuse());
        addUserRequestReq.setStatus(recallRequest.getStatus());
        addUserRequestReq.setRequestId(recallRequest.getRequestId());
        addUserRequestReq.setGroupId(recallRequest.getGroupId());
        addUserRequestReq.setAddGroupId(recallRequest.getAddGroupId());
        return addUserRequestReq;
    }
}























