package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jzsg.bussiness.JServiceImpl;
import com.jzsg.bussiness.model.ReqModel;
import com.jzsg.bussiness.model.ResModel;
import com.matrictime.network.api.modelVo.*;
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
import static com.matrictime.network.base.ErrorMessageContants.SAVE_FAIL_MSG;
import static com.matrictime.network.base.ErrorMessageContants.VERIFY_FAIL_MSG;
import static com.matrictime.network.base.UcConstants.*;
import static com.matrictime.network.base.DataConfig.*;
import static com.matrictime.network.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.SYSTEM_IM;
import static com.matrictime.network.constant.DataConstants.SYSTEM_UC;

@Service
@Slf4j
public class LoginServiceImpl extends SystemBaseService implements LoginService {


    @Autowired
    private CommonService commonService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private UserExtMapper userExtMapper;

    @Value("${sign.ip}")
    private String signIp;

    @Value("${sign.port}")
    private String signPort;

    @Value("${sign.verifyUrl}")
    private String verifyUrl;

    @Value("${sign.saveUrl}")
    private String saveUrl;

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
        try {
            req.setUserId(SnowFlake.nextId_String());
            result = buildResult(commonRegister(req));
        }catch (SystemException e){
            log.error("LoginServiceImpl.register SystemException:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }catch (Exception e){
            log.error("LoginServiceImpl.register Exception:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
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
        user.setLoginAccount(req.getLoginAccount());
        user.setNickName(req.getNickName());
        if (REGISTER_TYPE_COM.equals(req.getRegisterType())){
            user.setUserId(req.getUserId());
            user.setPassword(req.getPassword());
            user.setEmail(req.getEmail());
            user.setPhoneNumber(req.getPhoneNumber());
            user.setUserType(USER_TYPE_REG);
            user.setIdType(req.getIdType());
            user.setIdNo(req.getIdNo());
            user.setLoginAppCode(SYSTEM_JZDQ);
            user.setSex(req.getSex());
        }else if (REGISTER_TYPE_CA.equals(req.getRegisterType())){
            CAVo caVo = req.getCaVo();
            String contents = caVo.getContents();
            String content_type = caVo.getContent_type();
            PublicDataVo public_data = caVo.getPublic_data();
            caVo.setPublic_data(null);
            caVo.setContents(null);
            caVo.setContent_type(null);
            String savePost = HttpClientUtil.post(signIp + KEY_SPLIT + signPort + saveUrl, JSONObject.toJSONString(caVo));
            JSONObject saveRes = JSONObject.parseObject(savePost);
            log.info("ca保存签名结果："+saveRes.toJSONString());
            boolean saveFlag = false;
            if (saveRes != null) {
                Object saveCode = saveRes.get("code");
                if (saveCode != null && saveCode instanceof Integer) {
                    if (0 == (Integer)saveCode){
                        saveFlag = true;
                    }
                }
            }
            if (saveFlag){
                caVo.setContents(contents);
                caVo.setContent_type(content_type);
                caVo.setPublic_data(public_data);
                caVo.setSign_args(null);
                String verifyPost = HttpClientUtil.post(signIp + KEY_SPLIT + signPort + verifyUrl, JSONObject.toJSONString(caVo));
                JSONObject verifyRes = JSONObject.parseObject(verifyPost);
                log.info("ca验签结果："+verifyRes.toJSONString());
                boolean verifyFlag = false;
                if (verifyRes != null) {
                    Object verifyCode = verifyRes.get("code");
                    if (verifyCode != null && verifyCode instanceof Integer) {
                        if (0 == (Integer)verifyCode){
                            verifyFlag = true;
                        }
                    }
                }
                if (!verifyFlag){
                    throw new SystemException(VERIFY_FAIL_MSG);
                }
            }else {
                throw new SystemException(SAVE_FAIL_MSG);
            }
        }


        userMapper.insertSelective(user);
        RegisterResp resp = new RegisterResp();
        resp.setUserId(user.getUserId());

        return resp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<LoginResp> login(LoginReq req) {
        Result result;
        String userId = "";
        try {
            result = buildResult(commonLogin(req));

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

        return result;
    }

    private LoginResp commonLogin(LoginReq req)throws Exception{
        // 参数校验
        checkLoginParam(req);
        LoginResp resp = new LoginResp();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginAccountEqualTo(req.getLoginAccount());

        List<User> users = userMapper.selectByExample(userExample);
        // 查询用户是否存在
        if(CollectionUtils.isEmpty(users)){
            throw new SystemException(ErrorMessageContants.USERNAME_NO_EXIST_MSG);
        }else {
            User user = users.get(0);
            // 判断用户是否已登录
            if (LOGIN_STATUS_IN.equals(user.getLoginStatus())){
                throw new SystemException(ErrorMessageContants.USER_LOGIN_MSG);
            }
            switch (req.getLoginType()){
                case LOGIN_TYPE_ACCOUNT:
                    if (!user.getPassword().equals(req.getPassword())){
                        throw new SystemException(ErrorMessageContants.PASSWORD_ERROR_MSG);
                    }
                    break;
                case LOGIN_TYPE_CA:

                    // ca保存签名
                    CAVo caVo = req.getCaVo();
                    String contents = caVo.getContents();
                    String content_type = caVo.getContent_type();
                    PublicDataVo public_data = caVo.getPublic_data();
                    caVo.setPublic_data(null);
                    caVo.setContents(null);
                    caVo.setContent_type(null);
                    String savePost = HttpClientUtil.post(signIp + KEY_SPLIT + signPort + saveUrl, JSONObject.toJSONString(caVo));
                    JSONObject saveRes = JSONObject.parseObject(savePost);
                    log.info("ca保存签名结果："+saveRes.toJSONString());
                    boolean saveFlag = false;
                    if (saveRes != null) {
                        Object saveCode = saveRes.get("code");
                        if (saveCode != null && saveCode instanceof Integer) {
                            if (0 == (Integer)saveCode){
                                saveFlag = true;
                            }
                        }
                    }
                    if (saveFlag){
                        // ca验签
                        caVo.setContents(contents);
                        caVo.setContent_type(content_type);
                        caVo.setPublic_data(public_data);
                        caVo.setSign_args(null);
                        String verifyPost = HttpClientUtil.post(signIp + KEY_SPLIT + signPort + verifyUrl, JSONObject.toJSONString(caVo));
                        JSONObject verifyRes = JSONObject.parseObject(verifyPost);
                        log.info("ca验签结果："+verifyRes.toJSONString());
                        boolean verifyFlag = false;
                        if (verifyRes != null) {
                            Object verifyCode = verifyRes.get("code");
                            if (verifyCode != null && verifyCode instanceof Integer) {
                                if (0 == (Integer)verifyCode){
                                    verifyFlag = true;
                                }
                            }
                        }
                        if (!verifyFlag){
                            throw new SystemException(VERIFY_FAIL_MSG);
                        }
                    }else {
                        throw new SystemException(SAVE_FAIL_MSG);
                    }
                    break;
                default:
                    throw new SystemException("登录方式"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }

            user.setDeviceId(req.getDeviceId());
            user.setDeviceIp(req.getDeviceIp());
            user.setLoginStatus(LOGIN_STATUS_IN);
            user.setLoginAppCode(SYSTEM_JZDQ);
            userMapper.updateByPrimaryKeySelective(user);
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user,userVo);
            userVo.setPassword(null);

            // 生成token
            String token = buildToken(userVo, req.getDestination());
            setToken(user.getUserId(),token,req.getDestination());

            resp.setUser(userVo);
            resp.setToken(token);
        }
        return resp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result logout(LogoutReq req) {
        try {
            // 参数校验
            checkLogoutParam(req);
            User user = new User();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUserIdEqualTo(req.getUserId());
            user.setLoginStatus(DataConfig.LOGIN_STATUS_OUT);

            // 删除token
            delToken(req.getUserId(),req.getDestination());
            return buildResult(userMapper.updateByExampleSelective(user,userExample));
        }catch (Exception e){
            log.info("logout:{}",e.getMessage());
        }
        return buildResult(null);

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
                    req.setUrl(UcConstants.URL_BIND);
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
                    req.setUrl(UcConstants.URL_SYSLOGOUT);
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

//    @Override
//    public Result deleteUser(DeleteUserReq req) {
//        Result result;
//        try {
//
//            ReqUtil<DeleteUserReq> jsonUtil = new ReqUtil<>(req);
//            req = jsonUtil.jsonReqToDto(req);
//
//            switch (req.getDestination()){
//                case DESTINATION_OUT:
//                case DESTINATION_OUT_TO_IN:
//                    // 非密区删除用户
//                    commonDeleteUser(req);
//
//                    // 密区同步删除用户
//                    ReqModel reqModelF = new ReqModel();
//                    req.setDestination(UcConstants.DESTINATION_OUT_TO_IN_SYN);
//                    req.setUrl(UcConstants.URL_DELETEUSER);
//
//                    String paramF = JSONObject.toJSONString(req);
//                    reqModelF.setParam(paramF);
//                    ResModel resModel = JServiceImpl.syncSendMsg(reqModelF);
//                    log.info("非密区接收返回值LoginServiceImpl.deleteUser resModel:{}",JSONObject.toJSONString(resModel));
//                    Object returnValue = resModel.getReturnValue();
//                    if(returnValue != null && returnValue instanceof String){
//                        ResModel ResModelX = JSONObject.parseObject((String) returnValue, ResModel.class);
//                        result = JSONObject.parseObject(ResModelX.getReturnValue().toString(), Result.class);
//                        return result;
//                    }else {
//                        throw new Exception(ErrorMessageContants.RPC_RETURN_ERROR_MSG);
//                    }
//                case DESTINATION_IN:
//                    ReqModel reqModelM = new ReqModel();
//                    req.setDestination(UcConstants.DESTINATION_FOR_DES);
//                    req.setUrl(UcConstants.URL_DELETEUSER);
//                    String paramM = JSONObject.toJSONString(req);
//                    reqModelM.setParam(paramM);
//                    ResModel resModelM = JServiceImpl.syncSendMsg(reqModelM);
//
//                    log.info("非密区接收返回值LoginServiceImpl.deleteUser resModel:{}",resModelM.getReturnValue());
//                    Object returnValueM = resModelM.getReturnValue();
//                    if(returnValueM != null && returnValueM instanceof String){
//                        ResModel syncResModel = JSONObject.parseObject((String) returnValueM, ResModel.class);
//                        Result<RegisterResp> returnRes = JSONObject.parseObject(syncResModel.getReturnValue().toString(),new TypeReference<Result<RegisterResp>>(){});
//                        log.info("非密区接收返回值LoginServiceImpl.deleteUser returnRes:{}",returnRes.toString());
//                        if(returnRes.isSuccess()){
//                            // 密区/非密区同步用户
//                            RegisterResp resultObj = returnRes.getResultObj();
//                            RegisterReq req1 = resultObj.getRegisterReq();
//                            req1.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
//                            String post = HttpClientUtil.post(UcConstants.URL_DELETEUSER, JSONObject.toJSONString(req1));
//                            log.info("非密区接收密区/非密区同步返回值LoginServiceImpl.deleteUser post:{}",post);
//                            return JSONObject.parseObject(post,Result.class);
//                        }else {
//                            throw new Exception(ErrorMessageContants.RPC_RETURN_ERROR_MSG);
//                        }
//                    }else {
//                        throw new Exception(ErrorMessageContants.RPC_RETURN_ERROR_MSG);
//                    }
//
//                case UcConstants.DESTINATION_OUT_TO_IN_SYN:
//                    DeleteUserReq desReq2 = new DeleteUserReq();
//                    BeanUtils.copyProperties(req,desReq2);
//                    commonDeleteUser(desReq2);
//                    result = buildResult(null);
//                    break;
//
//                case UcConstants.DESTINATION_FOR_DES:
//                    // 入参解密
//
//                    ReqUtil<DeleteUserReq> reqUtil = new ReqUtil<>(req);
//                    DeleteUserReq desReq = reqUtil.decryJsonToReq(req);
//                    log.info("deleteUser密区解密结果desReq:{}",JSONObject.toJSONString(desReq));
//                    // 返回值加密
//
//                    result = buildResult(null);
//                    break;
//                default:
//                    throw new Exception("Destination"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
//
//            }
//        }catch (SystemException e){
//            log.error("LoginServiceImpl.deleteUser SystemException:{}",e.getMessage());
//            result = failResult(e);
//        }catch (Exception e){
//            log.error("LoginServiceImpl.deleteUser Exception:{}",e.getMessage());
//            result = failResult("");
//        }
//
//        return result;
//    }

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
        if (!ParamCheckUtil.checkVoStrBlank(req.getPhoneNumber())){
            UserExample.Criteria criteria3 = userExample.createCriteria();
            criteria3.andPhoneNumberEqualTo(req.getPhoneNumber()).andIsExistEqualTo(DataConstants.IS_EXIST);
            userExample.or(criteria3);
        }
        if (!ParamCheckUtil.checkVoStrBlank(req.getIdType()) && !ParamCheckUtil.checkVoStrBlank(req.getIdNo())){
            UserExample.Criteria criteria4 = userExample.createCriteria();
            criteria4.andIdTypeEqualTo(req.getIdType()).andIdNoEqualTo(req.getIdNo()).andIsExistEqualTo(DataConstants.IS_EXIST);
            userExample.or(criteria4);
        }

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
        if (ParamCheckUtil.checkVoStrBlank(req.getLoginType())){
            throw new Exception("loginType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (LOGIN_TYPE_ACCOUNT.equals(req.getLoginType())){
//            if(ParamCheckUtil.checkVoStrBlank(req.getSid())){
//                throw new Exception("sid"+ErrorMessageContants.PARAM_IS_NULL_MSG);
//            }
//            if (ParamCheckUtil.checkVoStrBlank(req.getLId())){
//                throw new Exception("lid"+ErrorMessageContants.PARAM_IS_NULL_MSG);
//            }
            if(ParamCheckUtil.checkVoStrBlank(req.getLoginAccount())){
                throw new SystemException("用户名"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if(ParamCheckUtil.checkVoStrBlank(req.getPassword())){
                throw new SystemException("密码"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }else if (LOGIN_TYPE_CA.equals(req.getLoginType())){
            if(ParamCheckUtil.checkVoStrBlank(req.getLoginAccount())){
                throw new SystemException("用户名"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }else {
            throw new SystemException("登录方式"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
        }

    }

    private void checkRegisterParam(RegisterReq req) throws Exception {
        if (req == null){
            throw new Exception("req"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getRegisterType() == null || req.getRegisterType().isEmpty()){
            throw new Exception("注册方式"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (REGISTER_TYPE_COM.equals(req.getRegisterType())){
            if (req.getLoginAccount() == null || req.getLoginAccount().isEmpty()){
                throw new SystemException("用户名"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (req.getPassword() == null || req.getPassword().isEmpty()){
                throw new SystemException("密码"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (req.getEmail() == null || req.getEmail().isEmpty()){
                throw new SystemException("邮箱"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (req.getPhoneNumber() == null || req.getPhoneNumber().isEmpty()){
                throw new SystemException("手机"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (req.getSex() == null || req.getSex().isEmpty()){
                throw new SystemException("性别"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (req.getIdType() == null || req.getIdType().isEmpty()){
                throw new SystemException("证件类型"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (req.getIdNo() == null || req.getIdNo().isEmpty()){
                throw new SystemException("证件号"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }else if (REGISTER_TYPE_CA.equals(req.getRegisterType())){
            if (req.getLoginAccount() == null || req.getLoginAccount().isEmpty()){
                throw new SystemException("用户名"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }else {
            throw new Exception("注册方式"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
        }

    }

    public void setToken(String userId, String token, String destination){
        StringBuffer sb = new StringBuffer(SYSTEM_UC);
        sb.append(USER_LOGIN_JWT_TOKEN).append(KEY_SPLIT_UNDERLINE).append(userId);
        log.info("LoginServiceImpl.putToken key:{},token:{}", sb.toString(),token);
        redisTemplate.opsForValue().set(sb.toString(),token);
    }

    public void delToken(String userId, String destination){
        StringBuffer sb = new StringBuffer(SYSTEM_UC);
        sb.append(USER_LOGIN_JWT_TOKEN).append(KEY_SPLIT_UNDERLINE).append(userId);
        log.info("LoginServiceImpl.removeToken key:{}", sb);
        redisTemplate.delete(sb.toString());
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
