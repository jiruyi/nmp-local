package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jzsg.bussiness.JServiceImpl;
import com.jzsg.bussiness.model.ReqModel;
import com.jzsg.bussiness.model.ResModel;
import com.matrictime.network.api.modelVo.GroupVo;
import com.matrictime.network.api.modelVo.UserGroupVo;
import com.matrictime.network.api.request.BindReq;
import com.matrictime.network.api.request.GroupReq;
import com.matrictime.network.api.request.UserGroupReq;
import com.matrictime.network.api.request.VerifyReq;
import com.matrictime.network.api.response.GroupResp;
import com.matrictime.network.api.response.RegisterResp;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.base.util.ReqUtil;
import com.matrictime.network.domain.CommonService;
import com.matrictime.network.domain.GroupDomainService;
import com.matrictime.network.domain.UserGroupDomianService;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GroupServiceImpl extends SystemBaseService implements GroupService {
    @Autowired
    GroupDomainService groupDomainService;
    @Autowired
    UserGroupDomianService userGroupDomianService;

    @Value("${app.innerUrl}")
    private String url;

    @Autowired
    CommonService commonService;

    @Override
    @Transactional
    public Result<Integer> createGroup(GroupReq groupReq) {
        ReqUtil<GroupReq> jsonUtil = new ReqUtil<>(groupReq);
        groupReq = jsonUtil.jsonReqToDto(groupReq);
        Result result;
        try {
            switch (groupReq.getDestination()){
                case UcConstants.DESTINATION_OUT:
                    result = commonCreateGroup(groupReq);
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    groupReq.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    groupReq.setUrl(url+UcConstants.URL_CREATEGROUP);
                    String param = JSONObject.toJSONString(groupReq);
                    log.info("非密区向密区发送请求参数param:{}",param);
                    reqModel.setParam(param);
                    ResModel resModel = JServiceImpl.syncSendMsg(reqModel);
                    log.info("非密区接收密区返回值ResModel:{}",JSONObject.toJSONString(resModel));
                    Object returnValue = resModel.getReturnValue();
                    if(returnValue != null && returnValue instanceof String){
                        ResModel syncResModel = JSONObject.parseObject((String) returnValue, ResModel.class);
                        result = JSONObject.parseObject(syncResModel.getReturnValue().toString(), Result.class);
                    }else {
                        throw new SystemException("createGroup"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                    break;
                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密
                    ReqUtil<GroupReq> reqUtil = new ReqUtil<>(groupReq);
                    GroupReq groupReq1 = reqUtil.decryJsonToReq(groupReq);
                    result = commonCreateGroup(groupReq1);
                    // 返回值加密

                    break;
                default:
                    throw new SystemException("Destination"+ ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
        }catch (SystemException e){
            log.error("createGroup Exception:{}",e.getMessage());
            result = failResult(e);
        }
        catch (Exception e){
            log.error("createGroup Exception:{}",e.getMessage());
            result = failResult("");
        }
        try {
            result = commonService.encrypt(groupReq.getCommonKey(), groupReq.getDestination(), result);
        }catch (Exception e){
            log.info("基础平台加密异常:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }


    @Override
    @Transactional
    public Result<Integer> modifyGroup(GroupReq groupReq) {
        ReqUtil<GroupReq> jsonUtil = new ReqUtil<>(groupReq);
        groupReq = jsonUtil.jsonReqToDto(groupReq);
        Result result;
        try {
            switch (groupReq.getDestination()){
                case UcConstants.DESTINATION_OUT:
                    result = commonModifyGroup(groupReq);
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    groupReq.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    groupReq.setUrl(url+UcConstants.URL_MODIFYGROUP);
                    String param = JSONObject.toJSONString(groupReq);
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
                        throw new SystemException("modifyGroup"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                    break;
                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密
                    ReqUtil<GroupReq> reqUtil = new ReqUtil<>(groupReq);
                    GroupReq groupReq1 = reqUtil.decryJsonToReq(groupReq);
                    result = commonModifyGroup(groupReq1);
                    // 返回值加密
                    break;
                default:
                    throw new SystemException("Destination"+ ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
        }catch (SystemException e){
            log.error("modifyGroup Exception:{}",e.getMessage());
            result = failResult(e);
        }
        catch (Exception e){
            log.error("modifyGroup Exception:{}",e.getMessage());
            result = failResult("");
        }
        try {
            result = commonService.encrypt(groupReq.getCommonKey(), groupReq.getDestination(), result);
        }catch (Exception e){
            log.info("基础平台加密异常:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    @Transactional
    public Result<Integer> deleteGroup(GroupReq groupReq) {
        ReqUtil<GroupReq> jsonUtil = new ReqUtil<>(groupReq);
        groupReq = jsonUtil.jsonReqToDto(groupReq);
        Result result;
        try {
            switch (groupReq.getDestination()){
                case UcConstants.DESTINATION_OUT:
                    result = commonDeleteGroup(groupReq);
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    groupReq.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    groupReq.setUrl(url+UcConstants.URL_DELETEGROUP);
                    String param = JSONObject.toJSONString(groupReq);
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
                        throw new SystemException("deleteGroup"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                    break;

                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密
                    ReqUtil<GroupReq> reqUtil = new ReqUtil<>(groupReq);
                    GroupReq groupReq1 = reqUtil.decryJsonToReq(groupReq);
                    result = commonDeleteGroup(groupReq1);
                    // 返回值加密

                    break;
                default:
                    throw new SystemException("Destination"+ ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
        }catch (SystemException e){
            log.error("deleteGroup Exception:{}",e.getMessage());
            result = failResult(e);
        }
        catch (Exception e){
            log.error("deleteGroup Exception:{}",e.getMessage());
            result = failResult("");
        }
        try {
            result = commonService.encrypt(groupReq.getCommonKey(), groupReq.getDestination(), result);
        }catch (Exception e){
            log.info("基础平台加密异常:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<GroupResp> queryGroup(GroupReq groupReq) {
        ReqUtil<GroupReq> jsonUtil = new ReqUtil<>(groupReq);
        groupReq = jsonUtil.jsonReqToDto(groupReq);
        Result result;
        try {
            switch (groupReq.getDestination()){
                case UcConstants.DESTINATION_OUT:
                    result = commonQueryGroup(groupReq);
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    groupReq.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    groupReq.setUrl(url+UcConstants.URL_QUERYGROUP);
                    String param = JSONObject.toJSONString(groupReq);
                    log.info("非密区向密区发送请求参数param:{}",param);
                    reqModel.setParam(param);
                    ResModel resModel = JServiceImpl.syncSendMsg(reqModel);
                    Object returnValue = resModel.getReturnValue();
                    if(returnValue != null && returnValue instanceof String){
                        ResModel syncResModel = JSONObject.parseObject((String) returnValue, ResModel.class);
                        result = JSONObject.parseObject(syncResModel.getReturnValue().toString(), Result.class);
                        //result = JSONObject.parseObject((String) returnValue, Result.class);
                    }else {
                        throw new SystemException("queryGroup"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                    log.info("非密区接收密区返回值ResModel:{}",JSONObject.toJSONString(resModel));
                    break;
                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密
                    ReqUtil<GroupReq> reqUtil = new ReqUtil<>(groupReq);
                    GroupReq groupReq1 = reqUtil.decryJsonToReq(groupReq);
                    result = commonQueryGroup(groupReq1);
                    // 返回值加密
                    break;
                default:
                    throw new SystemException("Destination"+ ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
        }catch (SystemException e){
            log.error("queryGroup Exception:{}",e.getMessage());
            result = failResult(e);
        }
        catch (Exception e){
            log.error("queryGroup Exception:{}",e.getMessage());
            result = failResult("");
        }
        try {
            result = commonService.encrypt(groupReq.getCommonKey(), groupReq.getDestination(), result);
        }catch (Exception e){
            log.info("基础平台加密异常:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    public Result<Integer> commonCreateGroup(GroupReq groupReq)throws Exception {
        Result<Integer> result;
        groupReq.setOwner(groupReq.getUserId());
        result = buildResult(groupDomainService.createGroup(groupReq));
        return result;
    }

    private Result<Integer> commonModifyGroup(GroupReq groupReq) throws Exception {
        Result<Integer> result;
//        try {
            groupReq.setOwner(groupReq.getUserId());
            result = buildResult(groupDomainService.modifyGroup(groupReq));
//        }catch (Exception e){
//            log.info("组修改异常",e.getMessage());
//            result = failResult(e);
//        }
        return result;
    }
    public Result<Integer> commonDeleteGroup(GroupReq groupReq) throws Exception {
        Result<Integer> result;
//        try {
            groupReq.setOwner(groupReq.getUserId());
            result = buildResult(groupDomainService.deleteGroup(groupReq));
//        }catch (Exception e){
//            log.info("组删除异常",e.getMessage());
//            result = failResult(e);
//        }
        return result;
    }

    public Result<GroupResp> commonQueryGroup(GroupReq groupReq) throws Exception {
        Result<GroupResp> result;
//        try {
            groupReq.setOwner(groupReq.getUserId());
            List<GroupVo> groupVoList = new ArrayList<>();
            groupVoList = groupDomainService.queryGroup(groupReq);
            List<String>groupIds = new ArrayList<>();
            for (GroupVo groupVo : groupVoList) {
                groupIds.add(String.valueOf(groupVo.getGroupId()));
            }
            Map<String, List<UserGroupVo>> map = userGroupDomianService.queryUserGroupByGroupIds(groupIds,groupReq.getOwner());
            for (GroupVo groupVo : groupVoList) {
                if(map.get(String.valueOf(groupVo.getGroupId()))==null){
                    groupVo.setUserGroupVoList(new ArrayList<>());
                }else {
                    groupVo.setUserGroupVoList(map.get(String.valueOf(groupVo.getGroupId())));
                }
            }
            GroupResp groupResp = new GroupResp();
            groupResp.setGroupVoList(groupVoList);
            result = buildResult(groupResp);
//        }catch (Exception e){
//            log.info("组查询异常",e.getMessage());
//            result = failResult(e);
//        }
        return result;
    }




}
