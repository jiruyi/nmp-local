package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jzsg.bussiness.JServiceImpl;
import com.jzsg.bussiness.model.ReqModel;
import com.jzsg.bussiness.model.ResModel;
import com.matrictime.network.api.modelVo.GroupVo;
import com.matrictime.network.api.modelVo.UserVo;
import com.matrictime.network.api.request.*;
import com.matrictime.network.api.response.LoginResp;
import com.matrictime.network.api.response.RegisterResp;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.base.util.CheckUtil;
import com.matrictime.network.base.util.ReqUtil;
import com.matrictime.network.config.DataConfig;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.controller.WebSocketServer;
import com.matrictime.network.dao.mapper.GroupInfoMapper;
import com.matrictime.network.dao.mapper.UserMapper;
import com.matrictime.network.dao.model.GroupInfo;
import com.matrictime.network.dao.model.GroupInfoExample;
import com.matrictime.network.dao.model.User;
import com.matrictime.network.dao.model.UserExample;
import com.matrictime.network.domain.CommonService;
import com.matrictime.network.domain.GroupDomainService;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.LoginService;
import com.matrictime.network.util.HttpClientUtil;
import com.matrictime.network.util.ParamCheckUtil;
import com.matrictime.network.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.matrictime.network.config.DataConfig.LOGIN_STATUS_IN;

@Service
@Slf4j
public class LoginServiceImpl extends SystemBaseService implements LoginService {

    @Autowired
    private GroupDomainService groupDomainService;

    @Autowired
    private CommonService commonService;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Value("${app.innerUrl}")
    private String url;

    @Value("${app.outerUrl}")
    private String outUrl;

    @Autowired(required = false)
    private GroupInfoMapper groupInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<RegisterResp> register(RegisterReq req) {
        Result result;
        RegisterResp resp;
        try {

            ReqUtil<RegisterReq> jsonUtil = new ReqUtil<>(req);
            req = jsonUtil.jsonReqToDto(req);

            switch (req.getDestination()){
                case UcConstants.DESTINATION_OUT:
                case UcConstants.DESTINATION_OUT_TO_IN:
                    req.setUserId(SnowFlake.nextId_String());
                    // 非密区生成用户
                    commonRegister(req);

                    // 密区同步用户
                    ReqModel reqModelF = new ReqModel();
                    req.setDestination(UcConstants.DESTINATION_OUT_TO_IN_SYN);
                    req.setUrl(url+UcConstants.URL_REGISTER);

                    String paramF = JSONObject.toJSONString(req);
                    reqModelF.setParam(paramF);
                    ResModel resModel = JServiceImpl.syncSendMsg(reqModelF);
                    log.info("非密区接收返回值LoginServiceImpl.register resModel:{}",JSONObject.toJSONString(resModel));
                    req.setDestination(UcConstants.DESTINATION_DEFAULT);
                    Object returnValue = resModel.getReturnValue();
                    if(returnValue != null && returnValue instanceof String){
                        ResModel ResModelX = JSONObject.parseObject((String) returnValue, ResModel.class);
                        result = JSONObject.parseObject(ResModelX.getReturnValue().toString(), Result.class);
                        return result;
                    }else {
                        throw new SystemException("LoginServiceImpl.register"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModelM = new ReqModel();
                    req.setDestination(UcConstants.DESTINATION_FOR_DES);
                    req.setUrl(url+UcConstants.URL_REGISTER);
                    String paramM = JSONObject.toJSONString(req);
                    reqModelM.setParam(paramM);
                    ResModel resModelM = JServiceImpl.syncSendMsg(reqModelM);

                    log.info("非密区接收返回值LoginServiceImpl.register resModel:{}",resModelM.getReturnValue());
                    Object returnValueM = resModelM.getReturnValue();
                    if(returnValueM != null && returnValueM instanceof String){
                        ResModel syncResModel = JSONObject.parseObject((String) returnValueM, ResModel.class);
                        Result<RegisterResp> returnRes = JSONObject.parseObject(syncResModel.getReturnValue().toString(),new TypeReference<Result<RegisterResp>>(){});
                        log.info("非密区接收返回值LoginServiceImpl.register returnRes:{}",returnRes.toString());
                        if(returnRes.isSuccess()){
                            // 密区/非密区同步用户
                            RegisterResp resultObj = returnRes.getResultObj();
                            RegisterReq req1 = resultObj.getRegisterReq();
                            req1.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                            String post = HttpClientUtil.post(outUrl + UcConstants.URL_REGISTER, JSONObject.toJSONString(req1));
                            log.info("非密区接收密区/非密区同步返回值LoginServiceImpl.register post:{}",post);
                            return JSONObject.parseObject(post,Result.class);
                        }else {
                            throw new SystemException("LoginServiceImpl.register"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                        }
                    }else {
                        throw new SystemException("LoginServiceImpl.register"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }

                case UcConstants.DESTINATION_OUT_TO_IN_SYN:
                    RegisterReq desReq2 = new RegisterReq();
                    BeanUtils.copyProperties(req,desReq2);
                    resp = commonRegister(desReq2);
                    return buildResult(resp);

                case UcConstants.DESTINATION_FOR_DES:
                    // 入参解密

                    ReqUtil<RegisterReq> reqUtil = new ReqUtil<>(req);
//                    RegisterReq desReq = JSONObject.parseObject(req.getEncryptParam(),new TypeReference<RegisterReq>(){});
                    RegisterReq desReq = reqUtil.decryJsonToReq(req);
                    log.info("密区解密结果desReq:{}",JSONObject.toJSONString(desReq));
                    resp = new RegisterResp();
                    resp.setRegisterReq(desReq);
                    // 返回值加密

                    return buildResult(resp);
                default:
                    throw new SystemException("Destination"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);

            }
        }catch (SystemException e){
            log.error("LoginServiceImpl.register SystemException:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }catch (Exception e){
            log.error("LoginServiceImpl.register Exception:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }

        try {
            result = commonService.encryptForRegister(req.getSid(), req.getDestination(), result);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
            log.error("LoginServiceImpl.login register Exception:{}",e.getMessage());
        }
        return result;
    }

    private RegisterResp commonRegister(RegisterReq req){

        checkRegisterParam(req);

        // 判断用户是否存在
        boolean userExist = checkUserExist(req);
        if(userExist){
            throw new SystemException(ErrorMessageContants.USER_IS_EXIST_MSG);
        }

        User user = new User();
        user.setUserId(req.getUserId());
        user.setSid(req.getSid());
        user.setLoginAccount(req.getLoginAccount());
        user.setNickName(req.getNickName());
        user.setPassword(req.getPassword());
        user.setEmail(req.getEmail());
        user.setPhoneNumber(req.getPhoneNumber());
        user.setUserType(req.getUserType());
        user.setIdType(req.getIdType());
        user.setIdNo(req.getIdNo());
        user.setLoginAppCode(req.getLoginAppCode());
        user.setSex(req.getSex());
        userMapper.insertSelective(user);
        RegisterResp resp = new RegisterResp();
        resp.setUserId(user.getUserId());

        GroupReq groupReq = new GroupReq();
        groupReq.setOwner(req.getUserId());
        groupReq.setGroupName(DataConfig.DEFAULT_GROUP_NAME);
        groupReq.setDefaultGroup(true);
        Integer group = groupDomainService.createGroup(groupReq);

        return resp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<LoginResp> login(LoginReq req) {
        Result result;
        try {
            LoginResp resp = new LoginResp();

            ReqUtil<LoginReq> jsonUtil = new ReqUtil<>(req);
            req = jsonUtil.jsonReqToDto(req);

            switch (req.getDestination()){
                case UcConstants.DESTINATION_OUT:
                    resp =commonLogin(req);
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    req.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    req.setUrl(url+UcConstants.URL_LOGIN);
                    String param = JSONObject.toJSONString(req);
                    reqModel.setParam(param);
                    ResModel resModel = JServiceImpl.syncSendMsg(reqModel);

                    log.info("非密区接收返回值LoginServiceImpl.login resModel:{}",resModel.getReturnValue());

                    Object returnValue = resModel.getReturnValue();
                    if(returnValue != null && returnValue instanceof String){
                        ResModel syncResModel = JSONObject.parseObject((String) returnValue, ResModel.class);
                        Result returnRes = JSONObject.parseObject(syncResModel.getReturnValue().toString(),new TypeReference<Result>(){});
                        return returnRes;
                    }else {
                        throw new SystemException("LoginServiceImpl.login"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }

                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密

                    ReqUtil<LoginReq> reqUtil = new ReqUtil<>(req);
//                    LoginReq desReq = JSONObject.parseObject(req.getEncryptParam(),new TypeReference<LoginReq>(){});
                    LoginReq desReq = reqUtil.decryJsonToReq(req);
                    resp = commonLogin(desReq);
                    // 返回值加密

                    return buildResult(resp,null,resp.getUser().getUserId());
                default:
                    throw new SystemException("Destination"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);

            }
            result = buildResult(resp);
        }catch (SystemException e){
            log.error("LoginServiceImpl.login SystemException:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }catch (Exception e){
            log.error("LoginServiceImpl.login Exception:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }


        try {
            result = commonService.encryptForLogin(req, result);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
            log.error("LoginServiceImpl.login encrypt Exception:{}",e.getMessage());
        }
        return result;
    }

    private LoginResp commonLogin(LoginReq req){
        checkLoginParam(req);
        LoginResp resp = new LoginResp();
        UserExample userExample = new UserExample();
        switch (req.getLoginType()){
            case DataConfig.LOGIN_TYPE_USER:
                userExample.createCriteria().andUserIdEqualTo(req.getUserId());
                break;
            case DataConfig.LOGIN_TYPE_ACCOUNT:
                userExample.createCriteria().andLoginAccountEqualTo(req.getLoginAccount());
                break;
            default:
                throw new SystemException("LoginType"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
        }

        List<User> users = userMapper.selectByExample(userExample);
        if(CollectionUtils.isEmpty(users)){
            throw new SystemException(ErrorMessageContants.USERNAME_NO_EXIST_MSG);
        }else {
            User user = users.get(0);
            if(!user.getPassword().equals(req.getPassword())){
                throw new SystemException(ErrorMessageContants.PASSWORD_ERROR_MSG);
            }
            user.setDeviceId(req.getDeviceId());
            user.setDeviceIp(req.getDeviceIp());
            user.setLoginStatus(LOGIN_STATUS_IN);
            user.setLoginAppCode(req.getLoginAppCode());
            user.setSid(req.getSid());
            userMapper.updateByPrimaryKeySelective(user);
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user,userVo);
            userVo.setPassword(null);

            String userId = user.getUserId();
            GroupInfoExample groupInfoExample = new GroupInfoExample();
            groupInfoExample.createCriteria().andOwnerEqualTo(userId).andIsExistEqualTo(DataConstants.IS_EXIST);
            List<GroupInfo> groupInfos = groupInfoMapper.selectByExample(groupInfoExample);
            List<GroupVo> groupVos = new ArrayList<>(groupInfos.size());
            for (GroupInfo groupInfo : groupInfos) {
                GroupVo groupVo = new GroupVo();
                BeanUtils.copyProperties(groupInfo,groupVo);
                groupVos.add(groupVo);
            }
            resp.setUser(userVo);
            resp.setGroups(groupVos);
        }
        return resp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result logout(LogoutReq req) {
        Result result;
        try {

            ReqUtil<LogoutReq> jsonUtil = new ReqUtil<>(req);
            req = jsonUtil.jsonReqToDto(req);

            switch (req.getDestination()){
                case UcConstants.DESTINATION_OUT:
                    commonLogout(req);
                    webSocketOnClose(req.getCommonKey());
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    req.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    req.setUrl(url+UcConstants.URL_LOGOUT);
                    String param = JSONObject.toJSONString(req);
                    reqModel.setParam(param);
                    ResModel resModel = JServiceImpl.syncSendMsg(reqModel);
                    log.info("非密区接收返回值LoginServiceImpl.logout resModel:{}",JSONObject.toJSONString(resModel));

                    Object returnValue = resModel.getReturnValue();
                    if(returnValue != null && returnValue instanceof String){
                        ResModel syncResModel = JSONObject.parseObject((String) returnValue, ResModel.class);
                        Result returnRes = JSONObject.parseObject(syncResModel.getReturnValue().toString(),Result.class);
                        if(returnRes.isSuccess()){
                            webSocketOnClose(req.getCommonKey());
                        }
                        return returnRes;
                    }else {
                        throw new SystemException("LoginServiceImpl.logout"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }

                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密

                    ReqUtil<LogoutReq> reqUtil = new ReqUtil<>(req);
                    LogoutReq desReq = reqUtil.decryJsonToReq(req);
                    commonLogout(desReq);
                    // 返回值加密

                    return buildResult(null);
                default:
                    throw new SystemException("Destination"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);

            }


            result = buildResult(null);
        }catch (SystemException e){
            log.error("LoginServiceImpl.logout SystemException:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }catch (Exception e){
            log.error("LoginServiceImpl.logout Exception:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }

        try {
            result = commonService.encrypt(req.getCommonKey(), req.getDestination(), result);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
            log.error("LoginServiceImpl.logout encrypt Exception:{}",e.getMessage());
        }

        return result;
    }

    private void commonLogout(LogoutReq req){
        checkLogoutParam(req);
        User user = new User();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIdEqualTo(req.getUserId());
        user.setLoginStatus(DataConfig.LOGIN_STATUS_OUT);
        userMapper.updateByExampleSelective(user,userExample);
    }


    private void webSocketOnClose(String userId){
        try {
            WebSocketServer webSocketServer = WebSocketServer.getWebSocketMap().get(userId);
            if(webSocketServer != null){
                webSocketServer.serverClose();
            }
        }catch (Exception e){
            log.info("user:{},webSocketOnClose Exception:{}",userId,e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result bind(BindReq req) {
        Result result;
        try {

            ReqUtil<BindReq> jsonUtil = new ReqUtil<>(req);
            req = jsonUtil.jsonReqToDto(req);

            switch (req.getDestination()){
                case UcConstants.DESTINATION_OUT:
                    commonBind(req);
                    req.setDestination(UcConstants.DESTINATION_FOR_DES);
                    log.info("非密区reqHashCode:{}",System.identityHashCode(req));
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    req.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    req.setUrl(url+UcConstants.URL_BIND);
                    String param = JSONObject.toJSONString(req);
                    reqModel.setParam(param);
                    ResModel resModel = JServiceImpl.syncSendMsg(reqModel);
                    log.info("非密区接收返回值LoginServiceImpl.bind resModel:{},reqHashCode:{}",JSONObject.toJSONString(resModel),System.identityHashCode(req));

                    Object returnValue = resModel.getReturnValue();

                    if(returnValue != null && returnValue instanceof String){
                        ResModel syncResModel = JSONObject.parseObject((String) returnValue, ResModel.class);
                        Result returnRes = JSONObject.parseObject(syncResModel.getReturnValue().toString(),Result.class);
                        return returnRes;
                    }else {
                        throw new SystemException("LoginServiceImpl.bind"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }

                case UcConstants.DESTINATION_OUT_TO_IN:

                    // 入参解密
                    ReqUtil<BindReq> reqUtil = new ReqUtil<>(req);
                    BindReq desReq = reqUtil.decryJsonToReq(req);
                    commonBind(desReq);
                    // 返回值加密

                    return buildResult(null);
                default:
                    throw new SystemException("Destination"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);

            }

            result = buildResult(null);
        }catch (SystemException e){
            log.error("LoginServiceImpl.bind SystemException:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }catch (Exception e){
            log.error("LoginServiceImpl.bind Exception:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }

        try {
            result = commonService.encrypt(req.getCommonKey(), req.getDestination(), result);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
            log.error("LoginServiceImpl.bind encrypt Exception:{}",e.getMessage());
        }
        return result;
    }

    private void commonBind(BindReq req){
        checkBindParam(req);
        switch (req.getOprType()){
            case DataConfig.OPR_TYPE_BIND:
                Long bindId = checkUserForBind(req);
                User user = new User();
                user.setId(bindId);
                user.setlId(req.getLid());
                userMapper.updateByPrimaryKeySelective(user);
                break;
            case DataConfig.OPR_TYPE_UNBIND:
                Long unBindId = checkUserForUnBind(req);
                User unUser = new User();
                unUser.setId(unBindId);
                unUser.setlId("null");
                userMapper.updateByPrimaryKeySelective(unUser);
                break;
            default:
                throw new SystemException("OprType"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
        }
    }

    private Long checkUserForBind(BindReq req){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIdEqualTo(req.getUserId()).andIsExistEqualTo(DataConstants.IS_EXIST);
        List<User> users = userMapper.selectByExample(userExample);
        if(CollectionUtils.isEmpty(users)){
            throw new SystemException(ErrorMessageContants.USER_NO_EXIST_MSG);
        }
        User user = users.get(0);
        if(!ParamCheckUtil.checkVoStrBlank(user.getlId())){
            throw new SystemException(ErrorMessageContants.USER_BIND_MSG);
        }
        return user.getId();
    }

    private Long checkUserForUnBind(BindReq req){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIdEqualTo(req.getUserId()).andIsExistEqualTo(DataConstants.IS_EXIST);
        List<User> users = userMapper.selectByExample(userExample);
        if(CollectionUtils.isEmpty(users)){
            throw new SystemException(ErrorMessageContants.USER_NO_EXIST_MSG);
        }
        User user = users.get(0);
        if(ParamCheckUtil.checkVoStrBlank(user.getlId())){
            throw new SystemException(ErrorMessageContants.USER_UNBIND_MSG);
        }
        if (!user.getlId().equals(req.getLid())){
            throw new SystemException("本地用户不是当前绑定用户，无法解绑");
        }
        return user.getId();
    }

    private void checkBindParam(BindReq req){
        if(ParamCheckUtil.checkVoStrBlank(req.getUserId())){
            throw new SystemException("userId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getOprType())){
            throw new SystemException("oprType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getLid())){
            throw new SystemException("lid"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private boolean checkUserExist(RegisterReq req){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria1 = userExample.createCriteria();
        criteria1.andLoginAccountEqualTo(req.getLoginAccount()).andIsExistEqualTo(DataConstants.IS_EXIST);
//        UserExample.Criteria criteria2 = userExample.createCriteria();
//        criteria2.andEmailEqualTo(req.getEmail());
//        userExample.or(criteria2);
        UserExample.Criteria criteria3 = userExample.createCriteria();
        criteria3.andPhoneNumberEqualTo(req.getPhoneNumber()).andIsExistEqualTo(DataConstants.IS_EXIST);
        userExample.or(criteria3);
        UserExample.Criteria criteria4 = userExample.createCriteria();
        criteria4.andIdTypeEqualTo(req.getIdType()).andIdNoEqualTo(req.getIdNo()).andIsExistEqualTo(DataConstants.IS_EXIST);
        userExample.or(criteria4);

        List<User> userList = userMapper.selectByExample(userExample);
        if(CollectionUtils.isEmpty(userList)){
            return false;
        }
        return true;
    }

    private void checkLogoutParam(LogoutReq req){
        if(ParamCheckUtil.checkVoStrBlank(req.getUserId())){
            throw new SystemException("userId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkLoginParam(LoginReq req){
        if(null == req){
            throw new SystemException("req"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if(ParamCheckUtil.checkVoStrBlank(req.getSid())){
            throw new SystemException("sid"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if(ParamCheckUtil.checkVoStrBlank(req.getLoginAccount()) && ParamCheckUtil.checkVoStrBlank(req.getUserId())){
            throw new SystemException("LoginAccount/UserId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if(ParamCheckUtil.checkVoStrBlank(req.getPassword())){
            throw new SystemException("Password"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkRegisterParam(RegisterReq req) {
        if (req == null){
            throw new SystemException("RegisterReq"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getUserId() == null || req.getUserId().isEmpty()){
            throw new SystemException("UserId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getLoginAccount() == null || req.getLoginAccount().isEmpty()){
            throw new SystemException("LoginAccount"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getPassword() == null || req.getPassword().isEmpty()){
            throw new SystemException("Password"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getEmail() == null || req.getEmail().isEmpty()){
            throw new SystemException("Email"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getPhoneNumber() == null || req.getPhoneNumber().isEmpty()){
            throw new SystemException("PhoneNumber"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getUserType() == null || req.getUserType().isEmpty()){
            throw new SystemException("UserType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getIdType() == null || req.getIdType().isEmpty()){
            throw new SystemException("IdType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getIdNo() == null || req.getIdNo().isEmpty()){
            throw new SystemException("IdNo"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getSid() == null || req.getSid().isEmpty()){
            throw new SystemException("Sid"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }
}
