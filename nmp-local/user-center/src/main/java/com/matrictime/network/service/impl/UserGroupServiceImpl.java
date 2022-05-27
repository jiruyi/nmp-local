package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jzsg.bussiness.JServiceImpl;
import com.jzsg.bussiness.model.ReqModel;
import com.jzsg.bussiness.model.ResModel;
import com.matrictime.network.api.modelVo.UserGroupVo;
import com.matrictime.network.api.request.GroupReq;
import com.matrictime.network.api.request.UserGroupReq;
import com.matrictime.network.api.response.UserGroupResp;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.base.util.ReqUtil;
import com.matrictime.network.domain.CommonService;
import com.matrictime.network.domain.UserGroupDomianService;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.UserGroupService;
import com.matrictime.network.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserGroupServiceImpl extends SystemBaseService implements UserGroupService {
    @Autowired
    UserGroupDomianService userGroupDomianService;

    @Autowired
    private CommonService commonService;

    @Value("${app.innerUrl}")
    private String url;

    @Override
    @Transactional
    public Result<Integer> createUserGroup(UserGroupReq userGroupReq) {
        ReqUtil<UserGroupReq> jsonUtil = new ReqUtil<>(userGroupReq);
        userGroupReq = jsonUtil.jsonReqToDto(userGroupReq);
        Result result;
        try {
            switch (userGroupReq.getDestination()){
                case UcConstants.DESTINATION_OUT:
                    result = commonCreateUserGroup(userGroupReq);
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    userGroupReq.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    userGroupReq.setUrl(url+UcConstants.URL_CREATEUSERGROUP);
                    String param = JSONObject.toJSONString(userGroupReq);
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
                        throw new SystemException("createUserGroup"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                    break;
                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密
                    ReqUtil<UserGroupReq> reqUtil = new ReqUtil<>(userGroupReq);
                    UserGroupReq userGroupReq1 = reqUtil.decryJsonToReq(userGroupReq);
                    result = commonCreateUserGroup(userGroupReq1);
                    // 返回值加密
                    break;
                default:
                    throw new SystemException("Destination"+ ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
        }catch (SystemException e){
            log.error("createUserGroup Exception:{}",e.getMessage());
            result = failResult(e);
        } catch (Exception e){
            log.error("createUserGroup Exception:{}",e.getMessage());
            result = failResult("");
        }

        try {
            result = commonService.encrypt(userGroupReq.getCommonKey(), userGroupReq.getDestination(), result);
        } catch (Exception e) {
            log.error("基础平台加密异常:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    @Override
    @Transactional
    public Result<Integer> modifyUserGroup(UserGroupReq userGroupReq) {
        ReqUtil<UserGroupReq> jsonUtil = new ReqUtil<>(userGroupReq);
        userGroupReq = jsonUtil.jsonReqToDto(userGroupReq);
        Result result;
        try {
            switch (userGroupReq.getDestination()){
                case UcConstants.DESTINATION_OUT:
                    result = commonModifyUserGroup(userGroupReq);
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    userGroupReq.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    userGroupReq.setUrl(url+UcConstants.URL_MODIFYUSERGROUP);
                    String param = JSONObject.toJSONString(userGroupReq);
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
                    ReqUtil<UserGroupReq> reqUtil = new ReqUtil<>(userGroupReq);
                    UserGroupReq userGroupReq1 = reqUtil.decryJsonToReq(userGroupReq);
                    result = commonModifyUserGroup(userGroupReq1);
                    // 返回值加密
                    break;
                default:
                    throw new SystemException("Destination"+ ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }

        } catch (SystemException e){
            log.error("modifyUserGroup Exception:{}",e.getMessage());
            result = failResult(e);
        } catch (Exception e){
            log.error("modifyUserGroup Exception:{}",e.getMessage());
            result = failResult("");
        }
        try {
            result = commonService.encrypt(userGroupReq.getCommonKey(), userGroupReq.getDestination(), result);
        } catch (Exception e) {
            log.error("基础平台加密异常:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    @Transactional
    public Result<Integer> deleteUserGroup(UserGroupReq userGroupReq) {
        ReqUtil<UserGroupReq> jsonUtil = new ReqUtil<>(userGroupReq);
        userGroupReq = jsonUtil.jsonReqToDto(userGroupReq);
        Result result;
        try {
            switch (userGroupReq.getDestination()){
                case UcConstants.DESTINATION_OUT:
                    result = commonDeleteUserGroup(userGroupReq);
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    userGroupReq.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    userGroupReq.setUrl(url+UcConstants.URL_DELETEUSERGROUP);
                    String param = JSONObject.toJSONString(userGroupReq);
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
                    ReqUtil<UserGroupReq> reqUtil = new ReqUtil<>(userGroupReq);
                    UserGroupReq userGroupReq1 = reqUtil.decryJsonToReq(userGroupReq);
                    result = commonDeleteUserGroup(userGroupReq1);
                    // 返回值加密
                    break;
                default:
                    throw new SystemException("Destination"+ ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }

        } catch (SystemException e){
            log.error("deleteUserGroup Exception:{}",e.getMessage());
            result = failResult(e);
        } catch (Exception e){
            log.error("deleteUserGroup Exception:{}",e.getMessage());
            result = failResult("");
        }
        try {
            result = commonService.encrypt(userGroupReq.getCommonKey(), userGroupReq.getDestination(), result);
        } catch (Exception e) {
            log.error("基础平台加密异常:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    @Override
    public Result<UserGroupResp> queryUserGroup(UserGroupReq userGroupReq) {
        Result<UserGroupResp> result;
//        try {
            UserGroupResp userGroupResp = new UserGroupResp();
            List<UserGroupVo> userGroupVos = userGroupDomianService.queryUserGroup(userGroupReq);
            userGroupResp.setUserGroupVos(userGroupVos);
            result = buildResult(userGroupResp);
//        }catch (Exception e){
//            log.info("组用户查询异常",e.getMessage());
//            result = failResult(e);
//        }
        return result;
    }


    private Result<Integer> commonCreateUserGroup(UserGroupReq userGroupReq)throws Exception {
        Result<Integer> result;
//        try {
            result = buildResult(userGroupDomianService.createUserGroup(userGroupReq));
//        }catch (SystemException e){
//            log.error("组用户新增异常",e.getMessage());
//            result = failResult(e);
//        }catch (Exception e){
//            log.info("组用户新增异常",e.getMessage());
//            result = failResult("");
//        }
        return result;
    }

    private Result<Integer> commonModifyUserGroup(UserGroupReq userGroupReq)throws Exception {
        Result<Integer> result;
//        try {
            result = buildResult(userGroupDomianService.modifyUserGroup(userGroupReq));
//        }catch (Exception e){
//            log.info("组用户修改异常",e.getMessage());
//            result = failResult(e);
//        }
        return result;
    }

    private Result<Integer> commonDeleteUserGroup(UserGroupReq userGroupReq)throws Exception {
        Result<Integer> result;
//        try {
            result = buildResult(userGroupDomianService.deleteUserGroup(userGroupReq));
//        }catch (Exception e){
//            log.info("组用户删除异常",e.getMessage());
//            result = failResult(e);
//        }
        return result;
    }

}
