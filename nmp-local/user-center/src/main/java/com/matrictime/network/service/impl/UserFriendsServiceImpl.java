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
import com.matrictime.network.domain.UserDomainService;
import com.matrictime.network.domain.UserFriendsDomainService;
import com.matrictime.network.domain.UserGroupDomianService;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.UserFriendsService;
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
                        Result<UserFriendResp> returnRes = JSONObject.parseObject(syncResModel.getReturnValue().toString(),new TypeReference<Result<UserFriendResp>>(){});
                        if(returnRes.isSuccess()){
                            result = commonSelectUserFriend(userFriendReq);
                            return result;
                        }
                    }else {
                        throw new SystemException("UserFriendsServiceImpl.selectUserFriend"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                    break;
                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密

                    return commonSelectUserFriend(userFriendReq);
                // 返回值加密
                case UcConstants.DESTINATION_OUT_TO_IN_SYN:

                    return commonSelectUserFriend(userFriendReq);
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
    public Result<Integer> addFriends(AddUserRequestReq addUserRequestReq) {
        Result<Integer> result = new Result<>();
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
                        Result<Integer> returnRes = JSONObject.parseObject(syncResModel.getReturnValue().toString(),new TypeReference<Result<Integer>>(){});
                        if(returnRes.isSuccess()){
                            result = commonAddFriends(userFriendReq,addUserRequestReq,userRequest);
                            return result;
                        }
                    }else {
                        throw new SystemException("UserFriendsServiceImpl.addFriends"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                    break;
                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密

                    return commonAddFriends(userFriendReq,addUserRequestReq,userRequest);
                // 返回值加密
                case UcConstants.DESTINATION_OUT_TO_IN_SYN:

                    return commonAddFriends(userFriendReq,addUserRequestReq,userRequest);
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
    public Result<Integer> agreeAddFriedns(RecallRequest recallRequest) {
        Result<Integer> result = new Result<>();
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
                        Result<Integer> returnRes = JSONObject.parseObject(syncResModel.getReturnValue().toString(),new TypeReference<Result<Integer>>(){});
                        if(returnRes.isSuccess()){
                            result = commonAddFriends(userFriendReq,addUserRequestReq,userRequest);
                            return result;
                        }
                    }else {
                        throw new SystemException("UserFriendsServiceImpl.addFriends"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                    break;
                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密

                    return commonAddFriends(userFriendReq,addUserRequestReq,userRequest);
                // 返回值加密
                case UcConstants.DESTINATION_OUT_TO_IN_SYN:

                    return commonAddFriends(userFriendReq,addUserRequestReq,userRequest);
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
    private Result<Integer> commonAddFriends(UserFriendReq userFriendReq,AddUserRequestReq addUserRequestReq,
                                             UserRequest userRequest) {
        Result<Integer> result = new Result<>();
        UserVo userVo = userFriendsDomainService.selectUserInfo(userRequest);
        AddRequestVo addRequestVo = userFriendsDomainService.selectGroupId(addUserRequestReq);
        if(userVo.getAgreeFriend() == 0 || addUserRequestReq.getAgree() != null){
            if(addUserRequestReq.getAgree() == null && userVo.getAgreeFriend() == 0){
                if(addRequestVo != null){
                    if("2".equals(addRequestVo.getStatus())){
                        result.setErrorMsg("该用户已经被添加");
                        result.setSuccess(false);
                        return result;
                    }
                }
                addUserRequestReq.setStatus(AddUserRequestEnum.AGREE.getCode());
                userFriendsDomainService.addFriends(addUserRequestReq);
            }
            if(addUserRequestReq.getAgree() != null && userVo.getAgreeFriend() == 1){
                userFriendsDomainService.update(addUserRequestReq);
            }
            //判断user_friend表中是否有该好友
            List<UserFriendVo> userFriendVos = userFriendsDomainService.selectUserFriend(userFriendReq);
            if(CollectionUtils.isEmpty(userFriendVos)){
                userFriendsDomainService.insertFriend(userFriendReq);
            }
            if(addRequestVo != null){
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
            //判断user_group是否有该数据
            UserGroupReq userGroupReq = new UserGroupReq();
            userGroupReq.setUserId(addUserRequestReq.getAddUserId());
            GroupReq groupReq = new GroupReq();
            groupReq.setOwner(addUserRequestReq.getAddUserId());
            UserFriendReq friendReq = new UserFriendReq();
            //被添加好友把好友添加到组
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
            //判断被添加用户是否有申请添加人的好友
            friendReq.setUserId(addUserRequestReq.getAddUserId());
            friendReq.setFriendUserId(addUserRequestReq.getUserId());
            List<UserFriendVo> userFriendVo = userFriendsDomainService.selectUserFriend(friendReq);
            //查找被添加用户的默认组
            GroupVo groupVo = userFriendsDomainService.selectGroupInfo(groupReq);
            if(groupVo != null && addUserRequestReq.getAddGroupId() == null){
                UserGroupReq req1 = new UserGroupReq();
                req1.setGroupId(groupVo.getGroupId().toString());
                req1.setUserId(addUserRequestReq.getUserId());
                List<UserGroupVo> friendUserGroupVos = userGroupDomianService.queryUserGroup(req1);
                if(CollectionUtils.isEmpty(friendUserGroupVos)){
                    userGroupDomianService.createUserGroup(req1);
                    if(CollectionUtils.isEmpty(userFriendVo)){
                        userFriendsDomainService.insertFriend(friendReq);
                    }
                }
            }else {
                if(CollectionUtils.isEmpty(userFriendVo)){
                    userFriendsDomainService.insertFriend(friendReq);
                }
            }
            result.setResultObj(1);
        }
        if(userVo.getAgreeFriend() == 1 || addUserRequestReq.getRefuse() != null) {
            if(userVo.getAgreeFriend() == 1 && addUserRequestReq.getRefuse() == null && addUserRequestReq.getAgree() == null){
                addUserRequestReq.setStatus(AddUserRequestEnum.TOBECERTIFIED.getCode());
                userFriendsDomainService.addFriends(addUserRequestReq);
            }
            if(userVo.getAgreeFriend() == 1 && addUserRequestReq.getRefuse() != null && addUserRequestReq.getAgree() == null){
                userFriendsDomainService.update(addUserRequestReq);
            }
            result.setResultObj(2);
        }
        return result;
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
                    result = commonGetAddUserInfo(addUserRequestReq);
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
                        Result<Integer> returnRes = JSONObject.parseObject(syncResModel.getReturnValue().toString(),new TypeReference<Result<Integer>>(){});
                        if(returnRes.isSuccess()){
                            // 非密区同步用户
                            return buildResult(commonCancelUser(userRequest));
                        }
                    }else {
                        throw new SystemException("UserFriendsServiceImpl.modifyUserInfo"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                    break;
                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密

                     result.setResultObj(commonCancelUser(userRequest));
                     return result;
                    // 返回值加密
                case UcConstants.DESTINATION_OUT_TO_IN_SYN:
                    result.setResultObj(commonCancelUser(userRequest));
                    return result;

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
}























