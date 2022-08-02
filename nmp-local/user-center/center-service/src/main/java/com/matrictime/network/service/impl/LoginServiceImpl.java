package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jzsg.bussiness.JServiceImpl;
import com.jzsg.bussiness.model.ReqModel;
import com.jzsg.bussiness.model.ResModel;
import com.matrictime.network.api.modelVo.GroupVo;
import com.matrictime.network.api.modelVo.PushUserVo;
import com.matrictime.network.api.modelVo.UserVo;
import com.matrictime.network.api.request.*;
import com.matrictime.network.api.response.LoginResp;
import com.matrictime.network.api.response.RegisterResp;
import com.matrictime.network.base.DataConfig;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.base.util.JwtUtils;
import com.matrictime.network.base.util.ReqUtil;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.domain.CommonService;
import com.matrictime.network.dao.domain.GroupDomainService;
import com.matrictime.network.dao.mapper.GroupInfoMapper;
import com.matrictime.network.dao.mapper.UserFriendMapper;
import com.matrictime.network.dao.mapper.UserMapper;
import com.matrictime.network.dao.mapper.ext.UserExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.LoginService;
import com.matrictime.network.util.HttpClientUtil;
import com.matrictime.network.util.ParamCheckUtil;
import com.matrictime.network.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.matrictime.network.base.DataConfig.LOGIN_STATUS_IN;
import static com.matrictime.network.base.UcConstants.*;
import static com.matrictime.network.base.DataConfig.*;
import static com.matrictime.network.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.SYSTEM_IM;
import static com.matrictime.network.constant.DataConstants.SYSTEM_UC;

@Service
@Slf4j
public class LoginServiceImpl extends SystemBaseService implements LoginService {

    @Autowired
    private GroupDomainService groupDomainService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private UserExtMapper userExtMapper;

    @Value("${app.innerUrl}")
    private String url;

    @Value("${app.outerUrl}")
    private String outUrl;


    @Value("${token.timeOut}")
    private Integer timeOut;

    @Value("${im.pushTokenUrl}")
    private String pushTokenUrl;


    @Autowired(required = false)
    private GroupInfoMapper groupInfoMapper;

    @Autowired(required = false)
    private UserFriendMapper userFriendMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<RegisterResp> register(RegisterReq req) {
        Result result;
        RegisterResp resp;
        Boolean encryFlag = false;
        try {

            ReqUtil<RegisterReq> jsonUtil = new ReqUtil<>(req);
            req = jsonUtil.jsonReqToDto(req);

            switch (req.getDestination()){
                case DESTINATION_OUT:
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
                        throw new Exception(ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                case DESTINATION_IN:
                    encryFlag = true;
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
                            result = JSONObject.parseObject(post, Result.class);
                            if (result.isSuccess()){
                                req.setLoginAccount(req1.getLoginAccount());
                            }
                        }else {
                            throw new Exception(ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                        }
                    }else {
                        throw new Exception(ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                    break;
                case UcConstants.DESTINATION_OUT_TO_IN_SYN:
                    RegisterReq desReq2 = new RegisterReq();
                    BeanUtils.copyProperties(req,desReq2);
                    resp = commonRegister(desReq2);
                    result = buildResult(resp);
                    break;

                case UcConstants.DESTINATION_FOR_DES:
                    // 入参解密

                    ReqUtil<RegisterReq> reqUtil = new ReqUtil<>(req);
                    RegisterReq desReq = reqUtil.decryJsonToReq(req);
                    log.info("密区解密结果desReq:{}",JSONObject.toJSONString(desReq));
                    resp = new RegisterResp();
                    resp.setRegisterReq(desReq);

                    return buildResult(resp);

                case DESTINATION_FOR_ENC:

                    ReqUtil resUtil = new ReqUtil();
                    String resultObj = resUtil.encryJsonStringToReq(req.getEncryptParam(), req.getSid());
                    result = buildResult(resultObj);
                    break;
                default:
                    throw new Exception("Destination"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);

            }
        }catch (SystemException e){
            log.error("LoginServiceImpl.register SystemException:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }catch (Exception e){
            log.error("LoginServiceImpl.register Exception:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
        }

        try {
            if (encryFlag) {
                ReqModel reqModel = new ReqModel();
                req.setDestination(UcConstants.DESTINATION_FOR_ENC);
                req.setUrl(url+UcConstants.URL_REGISTER);
                req.setEncryptParam(JSONObject.toJSONString(result));

                String param = JSONObject.toJSONString(req);
                reqModel.setParam(param);
                ResModel resModel = JServiceImpl.syncSendMsg(reqModel);
                log.info("非密区接收结果加密返回值LoginServiceImpl.register resModel:{}",resModel.getReturnValue());
                Object returnValue = resModel.getReturnValue();
                if(returnValue != null && returnValue instanceof String){
                    ResModel ResModel = JSONObject.parseObject((String) returnValue, ResModel.class);
                    result = JSONObject.parseObject(ResModel.getReturnValue().toString(), Result.class);
                    if (result.isSuccess()){
                        return result;
                    }else {
                        throw new Exception(ErrorMessageContants.ENCRYPT_FAIL_MSG);
                    }
                }else {
                    throw new Exception(ErrorMessageContants.ENCRYPT_FAIL_MSG);
                }
            }
        } catch (Exception e) {
            if (!ParamCheckUtil.checkVoStrBlank(req.getLoginAccount())) {
                DeleteUserReq deleteUserReq = new DeleteUserReq();
                deleteUserReq.setOpSystem(SYSTEM_UC);
                deleteUserReq.setDeleteType(DataConfig.DELETE_USER_TYPE_ACCOUNT);
                deleteUserReq.setLoginAccount(req.getLoginAccount());
                Result delResp = deleteUser(deleteUserReq);
                if (!delResp.isSuccess()){
                    log.error("LoginServiceImpl.register Exception:注册返回值加密失败，数据回滚失败，请联系技术人员排查");
                }
            }
            result = failResult("");
            log.error("LoginServiceImpl.register Exception:{}",e.getMessage());
        }
        return result;
    }

    private RegisterResp commonRegister(RegisterReq req) throws Exception {

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
        String sid = "";
        String userId = "";
        String token = "";
        String encryFlag = DESTINATION_OUT;
        try {
            LoginResp resp = new LoginResp();

            ReqUtil<LoginReq> jsonUtil = new ReqUtil<>(req);
            req = jsonUtil.jsonReqToDto(req);

            switch (req.getDestination()){
                case DESTINATION_OUT:
                    resp =commonLogin(req);
                    userId = resp.getUser().getUserId();
                    JSONObject extendMsg = new JSONObject();
                    token = buildToken(resp.getUser(), DESTINATION_OUT);
                    List<String> pushOnlineUsers = getPushOnlineUsers(userId,DESTINATION_OUT);
                    extendMsg.put("userId",userId);
                    extendMsg.put("token",token);
                    extendMsg.put("pushOnlineUsers",pushOnlineUsers);
                    extendMsg.put("pushInfo",getpushInfo(resp.getUser()));
                    putToken(userId,token,DESTINATION_OUT);
                    result = buildResult(resp,null,null,extendMsg.toJSONString());
                    break;
                case DESTINATION_IN:
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
                        if (returnRes.isSuccess() && StringUtils.isNotBlank(returnRes.getExtendMsg())){
                            JSONObject jsonObject = JSONObject.parseObject(returnRes.getExtendMsg());
                            if (jsonObject.containsKey("userId") && jsonObject.containsKey("token")){
                                userId = jsonObject.getString("userId");
                                token = jsonObject.getString("token");
                                if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(token)){
                                    putToken(userId,token,DESTINATION_IN);
                                }
                            }
                        }
                        return returnRes;
                    }else {
                        throw new Exception("LoginServiceImpl.login"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }

                case DESTINATION_OUT_TO_IN:
                    encryFlag = DESTINATION_OUT_TO_IN;
                    // 入参解密

                    ReqUtil<LoginReq> reqUtil = new ReqUtil<>(req);
//                    LoginReq desReq = JSONObject.parseObject(req.getEncryptParam(),new TypeReference<LoginReq>(){});
                    LoginReq desReq = reqUtil.decryJsonToReq(req);
                    sid = desReq.getSid();
                    resp = commonLogin(desReq);
                    userId = resp.getUser().getUserId();
                    // 返回值加密
                    token = buildToken(resp.getUser(), DESTINATION_IN);
                    List<String> desPushOnlineUsers = getPushOnlineUsers(userId,DESTINATION_IN);
                    JSONObject desExtendMsg = new JSONObject();
                    desExtendMsg.put("sid",sid);
                    desExtendMsg.put("userId",userId);
                    desExtendMsg.put("token",token);
                    desExtendMsg.put("pushOnlineUsers",desPushOnlineUsers);
                    desExtendMsg.put("pushInfo",getpushInfo(resp.getUser()));
                    result = buildResult(resp,null,null,desExtendMsg.toJSONString());
                    break;
                default:
                    throw new Exception("Destination"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);

            }
        }catch (SystemException e){
            log.error("LoginServiceImpl.login SystemException:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (StringUtils.isNotBlank(userId)){
                removeToken(userId,req.getDestination());
            }
            result = failResult(e);
        }catch (Exception e){
            log.error("LoginServiceImpl.login Exception:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (StringUtils.isNotBlank(userId)){
                removeToken(userId,req.getDestination());
            }
            result = failResult("");
        }


        try {
            result = commonService.encryptForLogin(req, encryFlag, result);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
            log.error("LoginServiceImpl.login encrypt Exception:{}",e.getMessage());
        }
        return result;
    }

    private LoginResp commonLogin(LoginReq req)throws Exception{
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
                throw new Exception("LoginType"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
        }

        List<User> users = userMapper.selectByExample(userExample);
        if(CollectionUtils.isEmpty(users)){
            throw new SystemException(ErrorMessageContants.USERNAME_NO_EXIST_MSG);
        }else {
            User user = users.get(0);
            if (LOGIN_STATUS_IN.equals(user.getLoginStatus())){
                throw new SystemException(ErrorMessageContants.USER_LOGIN_MSG);
            }
//            if (!ParamCheckUtil.checkVoStrBlank(user.getlId()) && !user.getlId().equals(req.getLId())){
//                throw new SystemException(ErrorMessageContants.LOGIN_BIND_MSG);
//            }
            if (!user.getPassword().equals(req.getPassword())){
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
        String encryFlag = DESTINATION_OUT;
        try {

            ReqUtil<LogoutReq> jsonUtil = new ReqUtil<>(req);
            req = jsonUtil.jsonReqToDto(req);

            switch (req.getDestination()){
                case DESTINATION_OUT:
                    commonLogout(req);
                    removeToken(req.getUserId(),req.getDestination());
                    webSocketOnClose(req.getCommonKey()+KEY_SPLIT_UNDERLINE+DESTINATION_OUT);
                    result = buildResult(null);
                    break;
                case DESTINATION_IN:
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
                            if (StringUtils.isNotBlank(returnRes.getExtendMsg())){
                                removeToken(returnRes.getExtendMsg(),req.getDestination());
                            }
                            webSocketOnClose(req.getCommonKey()+KEY_SPLIT_UNDERLINE+DESTINATION_IN);
                        }
                        returnRes.setExtendMsg(null);
                        return returnRes;
                    }else {
                        throw new SystemException("LoginServiceImpl.logout"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }

                case UcConstants.DESTINATION_OUT_TO_IN:
                    encryFlag = DESTINATION_OUT_TO_IN;
                    // 入参解密

                    ReqUtil<LogoutReq> reqUtil = new ReqUtil<>(req);
                    LogoutReq desReq = reqUtil.decryJsonToReq(req);
                    commonLogout(desReq);
                    // 返回值加密


                    result = buildResult(null,null,null,desReq.getUserId());
                    break;
                default:
                    throw new Exception("Destination"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);

            }

        }catch (SystemException e){
            log.error("LoginServiceImpl.logout SystemException:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }catch (Exception e){
            log.error("LoginServiceImpl.logout Exception:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
        }

        try {
            result = commonService.encrypt(req.getCommonKey(), encryFlag, result);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
            log.error("LoginServiceImpl.logout encrypt Exception:{}",e.getMessage());
        }

        return result;
    }

    private void commonLogout(LogoutReq req) throws Exception{
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
        String encryFlag = DESTINATION_OUT;
        try {

            ReqUtil<BindReq> jsonUtil = new ReqUtil<>(req);
            req = jsonUtil.jsonReqToDto(req);

            switch (req.getDestination()){
                case DESTINATION_OUT:
                    commonBind(req);
                    req.setDestination(UcConstants.DESTINATION_FOR_DES);
                    result = buildResult(null);
                    break;
                case DESTINATION_IN:
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

                    encryFlag = DESTINATION_OUT_TO_IN;
                    // 入参解密
                    ReqUtil<BindReq> reqUtil = new ReqUtil<>(req);
                    BindReq desReq = reqUtil.decryJsonToReq(req);
                    commonBind(desReq);
                    // 返回值加密
                    result = buildResult(null);
                    break;
                default:
                    throw new Exception("Destination"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);

            }

        }catch (SystemException e){
            log.error("LoginServiceImpl.bind SystemException:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }catch (Exception e){
            log.error("LoginServiceImpl.bind Exception:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
        }

        try {
            result = commonService.encrypt(req.getCommonKey(), encryFlag, result);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
            log.error("LoginServiceImpl.bind encrypt Exception:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result syslogout(LogoutReq req) {
        Result result;
        try {
            switch (req.getDestination()){
                case DESTINATION_OUT:
                    commonLogout(req);
//                    webSocketOnClose(req.getCommonKey());
                    removeToken(req.getUserId(),req.getDestination());
                    break;
                case DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    req.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    req.setUrl(url+UcConstants.URL_SYSLOGOUT);
                    String param = JSONObject.toJSONString(req);
                    reqModel.setParam(param);
                    ResModel resModel = JServiceImpl.syncSendMsg(reqModel);
                    log.info("非密区接收返回值LoginServiceImpl.logout resModel:{}",JSONObject.toJSONString(resModel));

                    Object returnValue = resModel.getReturnValue();
                    if(returnValue != null && returnValue instanceof String){
                        ResModel syncResModel = JSONObject.parseObject((String) returnValue, ResModel.class);
                        Result returnRes = JSONObject.parseObject(syncResModel.getReturnValue().toString(),Result.class);
                        if(returnRes.isSuccess()){
                            if (StringUtils.isNotBlank(returnRes.getExtendMsg())){
                                removeToken(returnRes.getExtendMsg(),req.getDestination());
                            }
//                            webSocketOnClose(req.getCommonKey());
                        }
                        return returnRes;
                    }else {
                        throw new Exception("LoginServiceImpl.logout"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }

                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密
                    commonLogout(req);
                    // 返回值加密

                    return buildResult(null,null,null,req.getUserId());
                default:
                    throw new Exception("Destination"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);

            }


            result = buildResult(null);
        }catch (SystemException e){
            log.error("LoginServiceImpl.logout SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("LoginServiceImpl.logout Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result pushToken(PushTokenReq req){
        Result result;
        try {
            String postRes = "";
            switch (req.getType()){
                case PUSH_TYPE_TOKEN:
                case PUSH_TYPE_DISABLE_TOKEN:
                    postRes = HttpClientUtil.post(req.getUrl(), req.getBody());
                    break;
                default:
                    log.info("LoginServiceImpl.pushToken is not illegal type:{}",req.getType());
                    break;
            }
            log.info("LoginServiceImpl.pushToken postRes:{}",postRes);
            result = buildResult(null);
        } catch (Exception e) {
            log.error("LoginServiceImpl.pushToken error:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result deleteUser(DeleteUserReq req) {
        Result result;
        try {

            ReqUtil<DeleteUserReq> jsonUtil = new ReqUtil<>(req);
            req = jsonUtil.jsonReqToDto(req);

            switch (req.getDestination()){
                case DESTINATION_OUT:
                case DESTINATION_OUT_TO_IN:
                    // 非密区删除用户
                    commonDeleteUser(req);

                    // 密区同步删除用户
                    ReqModel reqModelF = new ReqModel();
                    req.setDestination(UcConstants.DESTINATION_OUT_TO_IN_SYN);
                    req.setUrl(url+UcConstants.URL_DELETEUSER);

                    String paramF = JSONObject.toJSONString(req);
                    reqModelF.setParam(paramF);
                    ResModel resModel = JServiceImpl.syncSendMsg(reqModelF);
                    log.info("非密区接收返回值LoginServiceImpl.deleteUser resModel:{}",JSONObject.toJSONString(resModel));
                    Object returnValue = resModel.getReturnValue();
                    if(returnValue != null && returnValue instanceof String){
                        ResModel ResModelX = JSONObject.parseObject((String) returnValue, ResModel.class);
                        result = JSONObject.parseObject(ResModelX.getReturnValue().toString(), Result.class);
                        return result;
                    }else {
                        throw new Exception(ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                case DESTINATION_IN:
                    ReqModel reqModelM = new ReqModel();
                    req.setDestination(UcConstants.DESTINATION_FOR_DES);
                    req.setUrl(url+UcConstants.URL_DELETEUSER);
                    String paramM = JSONObject.toJSONString(req);
                    reqModelM.setParam(paramM);
                    ResModel resModelM = JServiceImpl.syncSendMsg(reqModelM);

                    log.info("非密区接收返回值LoginServiceImpl.deleteUser resModel:{}",resModelM.getReturnValue());
                    Object returnValueM = resModelM.getReturnValue();
                    if(returnValueM != null && returnValueM instanceof String){
                        ResModel syncResModel = JSONObject.parseObject((String) returnValueM, ResModel.class);
                        Result<RegisterResp> returnRes = JSONObject.parseObject(syncResModel.getReturnValue().toString(),new TypeReference<Result<RegisterResp>>(){});
                        log.info("非密区接收返回值LoginServiceImpl.deleteUser returnRes:{}",returnRes.toString());
                        if(returnRes.isSuccess()){
                            // 密区/非密区同步用户
                            RegisterResp resultObj = returnRes.getResultObj();
                            RegisterReq req1 = resultObj.getRegisterReq();
                            req1.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                            String post = HttpClientUtil.post(outUrl + UcConstants.URL_DELETEUSER, JSONObject.toJSONString(req1));
                            log.info("非密区接收密区/非密区同步返回值LoginServiceImpl.deleteUser post:{}",post);
                            return JSONObject.parseObject(post,Result.class);
                        }else {
                            throw new Exception(ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                        }
                    }else {
                        throw new Exception(ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }

                case UcConstants.DESTINATION_OUT_TO_IN_SYN:
                    DeleteUserReq desReq2 = new DeleteUserReq();
                    BeanUtils.copyProperties(req,desReq2);
                    commonDeleteUser(desReq2);
                    result = buildResult(null);
                    break;

                case UcConstants.DESTINATION_FOR_DES:
                    // 入参解密

                    ReqUtil<DeleteUserReq> reqUtil = new ReqUtil<>(req);
                    DeleteUserReq desReq = reqUtil.decryJsonToReq(req);
                    log.info("deleteUser密区解密结果desReq:{}",JSONObject.toJSONString(desReq));
                    // 返回值加密

                    result = buildResult(null);
                    break;
                default:
                    throw new Exception("Destination"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);

            }
        }catch (SystemException e){
            log.error("LoginServiceImpl.deleteUser SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("LoginServiceImpl.deleteUser Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    private void commonDeleteUser(DeleteUserReq req) throws Exception{
        checkDeleteUserParam(req);
        UserExample userExample = new UserExample();
        switch (req.getDeleteType()){
            case DataConfig.DELETE_USER_TYPE_USERID:
                userExample.createCriteria().andUserIdEqualTo(req.getUserId());
                userMapper.deleteByExample(userExample);
                break;
            case DataConfig.DELETE_USER_TYPE_ACCOUNT:
                userExample.createCriteria().andLoginAccountEqualTo(req.getLoginAccount());
                userMapper.deleteByExample(userExample);
                break;
            case DataConfig.DELETE_USER_TYPE_PHONE:
                userExample.createCriteria().andPhoneNumberEqualTo(req.getPhoneNumber());
                userMapper.deleteByExample(userExample);
                break;
            default:
                throw new Exception("deleteType"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
        }
    }

    private void checkDeleteUserParam(DeleteUserReq req) throws Exception{
        if (ParamCheckUtil.checkVoStrBlank(req.getDeleteType())){
            throw new Exception("deleteType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getOpSystem())){
            throw new Exception("opSystem"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        switch (req.getDeleteType()){
            case DataConfig.DELETE_USER_TYPE_USERID:
                if (ParamCheckUtil.checkVoStrBlank(req.getUserId())){
                    throw new Exception("userId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
                }
                break;
            case DataConfig.DELETE_USER_TYPE_ACCOUNT:
                if (ParamCheckUtil.checkVoStrBlank(req.getLoginAccount())){
                    throw new Exception("LoginAccount"+ErrorMessageContants.PARAM_IS_NULL_MSG);
                }
                break;
            case DataConfig.DELETE_USER_TYPE_PHONE:
                if (ParamCheckUtil.checkVoStrBlank(req.getPhoneNumber())){
                    throw new Exception("PhoneNumber"+ErrorMessageContants.PARAM_IS_NULL_MSG);
                }
                break;
            default:
                throw new Exception(ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
        }
    }

    private void commonBind(BindReq req) throws Exception{
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
                userExtMapper.unbind(unUser);
                break;
            default:
                throw new Exception("OprType"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
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
            throw new SystemException(ErrorMessageContants.USER_BIND_IS_NOT_LOCAL);
        }
        return user.getId();
    }

    private void checkBindParam(BindReq req) throws Exception{
        if(ParamCheckUtil.checkVoStrBlank(req.getUserId())){
            throw new Exception("userId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getOprType())){
            throw new Exception("oprType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getLid())){
            throw new Exception("lid"+ErrorMessageContants.PARAM_IS_NULL_MSG);
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

    private void checkLogoutParam(LogoutReq req) throws Exception{
        if(ParamCheckUtil.checkVoStrBlank(req.getUserId())){
            throw new Exception("userId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkLoginParam(LoginReq req) throws Exception{
        if(null == req){
            throw new Exception("req"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if(ParamCheckUtil.checkVoStrBlank(req.getSid())){
            throw new Exception("sid"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getLId())){
            throw new Exception("lid"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if(ParamCheckUtil.checkVoStrBlank(req.getLoginAccount()) && ParamCheckUtil.checkVoStrBlank(req.getUserId())){
            throw new Exception("LoginAccount/UserId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if(ParamCheckUtil.checkVoStrBlank(req.getPassword())){
            throw new Exception("Password"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkRegisterParam(RegisterReq req) throws Exception {
        if (req == null){
            throw new Exception("req"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getLoginAccount() == null || req.getLoginAccount().isEmpty()){
            throw new SystemException("账号"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getPassword() == null || req.getPassword().isEmpty()){
            throw new SystemException("密码"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getEmail() == null || req.getEmail().isEmpty()){
            throw new SystemException("用户邮箱"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getPhoneNumber() == null || req.getPhoneNumber().isEmpty()){
            throw new SystemException("手机号码"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getUserType() == null || req.getUserType().isEmpty()){
            throw new Exception("UserType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getIdType() == null || req.getIdType().isEmpty()){
            throw new SystemException("证件类型"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getIdNo() == null || req.getIdNo().isEmpty()){
            throw new SystemException("证件号"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getSid() == null || req.getSid().isEmpty()){
            throw new Exception("Sid"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    /**
     * 生成token
     * @param user
     * @return
     */
    public String buildToken(UserVo user,String destination){
        return JwtUtils.createToken(user,destination);
    }

    public void putToken(String userId, String token, String destination){
        StringBuffer sb = new StringBuffer(SYSTEM_UC);
        sb.append(USER_LOGIN_JWT_TOKEN).append(KEY_SPLIT_UNDERLINE).append(userId).append(KEY_SPLIT_UNDERLINE).append(destination);
        log.info("LoginServiceImpl.putToken key:{},token:{}", sb.toString(),token);
        redisTemplate.opsForValue().set(sb.toString(),token,timeOut, TimeUnit.MINUTES);

        // im设置token
        JSONObject param = new JSONObject();
        param.put("userid",userId);
        param.put("token",token);
        param.put("type",PUSH_TYPE_TOKEN);
        param.put("scope",destination);
        PushTokenReq req = new PushTokenReq();
        req.setSystem(SYSTEM_IM);
        req.setType(PUSH_TYPE_TOKEN);
        req.setUrl(pushTokenUrl);
        req.setBody(param.toJSONString());
        pushToken(req);
    }

    public void removeToken(String userId, String destination){
        StringBuffer sb = new StringBuffer(SYSTEM_UC);
        if (DESTINATION_OUT_TO_IN.equals(destination)){
            destination = DESTINATION_IN;
        }
        sb.append(USER_LOGIN_JWT_TOKEN).append(KEY_SPLIT_UNDERLINE).append(userId).append(KEY_SPLIT_UNDERLINE).append(destination);
        log.info("LoginServiceImpl.removeToken key:{}", sb);
        redisTemplate.delete(sb.toString());

        JSONObject param = new JSONObject();
        param.put("type",PUSH_TYPE_DISABLE_TOKEN);
        param.put("userid",userId);
        param.put("scope",destination);
        PushTokenReq req = new PushTokenReq();
        req.setType(PUSH_TYPE_DISABLE_TOKEN);
        req.setUrl(pushTokenUrl);
        req.setSystem(SYSTEM_IM);
        req.setBody(param.toString());
        pushToken(req);
    }

    private List<String> getPushOnlineUsers(String userId, String destination){
        List<String> resUsers = new ArrayList<>();
        UserFriendExample example = new UserFriendExample();
        example.createCriteria().andUserIdEqualTo(userId).andIsExistEqualTo(IS_EXIST);
        List<UserFriend> userFriends = userFriendMapper.selectByExample(example);
        for (UserFriend userFriend:userFriends){
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUserIdEqualTo(userFriend.getFriendUserId()).andLoginStatusEqualTo(LOGIN_STATUS_IN).andIsExistEqualTo(IS_EXIST);
            List<User> users = userMapper.selectByExample(userExample);
            if (!CollectionUtils.isEmpty(users)){
                resUsers.add(users.get(0).getUserId()+KEY_SPLIT_UNDERLINE+destination);
            }
        }
        return  resUsers;
    }

    private PushUserVo getpushInfo(UserVo userVo){
        PushUserVo pushUserVo = new PushUserVo();
        BeanUtils.copyProperties(userVo,pushUserVo);
        pushUserVo.setFriendUserId(pushUserVo.getUserId());
        return pushUserVo;
    }
}
