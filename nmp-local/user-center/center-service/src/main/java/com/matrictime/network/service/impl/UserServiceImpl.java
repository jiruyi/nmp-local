package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jzsg.bussiness.JServiceImpl;
import com.jzsg.bussiness.model.ReqModel;
import com.jzsg.bussiness.model.ResModel;
import com.matrictime.network.api.modelVo.WsResultVo;
import com.matrictime.network.api.modelVo.WsSendVo;
import com.matrictime.network.api.request.*;
import com.matrictime.network.api.response.UserResp;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.base.util.JwtUtils;
import com.matrictime.network.base.util.ReqUtil;
import com.matrictime.network.base.DataConfig;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.domain.CommonService;
import com.matrictime.network.dao.domain.UserDomainService;
import com.matrictime.network.dao.mapper.UserMapper;
import com.matrictime.network.dao.model.User;
import com.matrictime.network.dao.model.UserExample;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.UserService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.regex.Pattern;

import static com.matrictime.network.base.UcConstants.*;
import static com.matrictime.network.constant.DataConstants.*;
import static com.matrictime.network.exception.ErrorMessageContants.NO_USER_BOUND_TEL;
import static com.matrictime.network.exception.ErrorMessageContants.PASSWORD_TWICE_INCONSISTENT;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/31 0031 16:24
 * @desc
 */
@Service
@Slf4j
public class UserServiceImpl   extends SystemBaseService implements UserService {

    @Autowired
    private UserDomainService userDomainService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CommonService commonService;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Value("${app.innerUrl}")
    private String url;


    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "0?(13|14|15|18)[0-9]{9}";

    public static final Integer lastSix = 6;


    /**
     * 校验手机号
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
      * @title modifyUserInfo
      * @param userRequest
      * @return com.matrictime.network.model.Result
      * @description 
      * @author jiruyi
      * @create 2022/4/7 0007 17:39
      */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result modifyUserInfo(UserRequest userRequest) {
        if(ObjectUtils.isEmpty(userRequest) || ObjectUtils.isEmpty(userRequest.getUserId())){
            return failResult(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIdEqualTo(userRequest.getUserId());

        List<User> users = userMapper.selectByExample(userExample);
        if(CollectionUtils.isEmpty(users)){
            return failResult(ErrorMessageContants.USER_NO_EXIST_MSG);
        }
        int n = userDomainService.modifyUserInfo(userRequest);
        return  buildResult(n);
    }

    /**
      * @title deleteFriend
      * @param deleteFriendReq
      * @return com.matrictime.network.model.Result
      * @description
      * @author jiruyi
      * @create 2022/4/12 0012 13:56
      */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteFriend(DeleteFriendReq deleteFriendReq) {
        Result result;
        String encryFlag = DESTINATION_OUT;
        try {

            ReqUtil<DeleteFriendReq> jsonUtil = new ReqUtil<>(deleteFriendReq);
            deleteFriendReq = jsonUtil.jsonReqToDto(deleteFriendReq);

            switch (deleteFriendReq.getDestination()){
                case DESTINATION_OUT:
                    result = commonDeleteFriend(deleteFriendReq);
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    deleteFriendReq.setDestination(DESTINATION_OUT_TO_IN);
                    deleteFriendReq.setUrl(url+UcConstants.URL_DELETEFRIEND);
                    String param = JSONObject.toJSONString(deleteFriendReq);
                    log.info("非密区向密区发送请求参数param:{}",param);
                    reqModel.setParam(param);
                    ResModel resModel = JServiceImpl.syncSendMsg(reqModel);
                    Object returnValue = resModel.getReturnValue();
                    log.info("非密区接收密区返回值ResModel:{}",returnValue);
                    if(returnValue != null && returnValue instanceof String){
                        ResModel syncResModel = JSONObject.parseObject((String) returnValue, ResModel.class);
                        Result returnRes = JSONObject.parseObject(syncResModel.getReturnValue().toString(),new TypeReference<Result>(){});
                        return returnRes;
                    }else {
                        throw new SystemException("UserServiceImpl.deleteFriend"+ErrorMessageContants.RPC_RETURN_ERROR_MSG);
                    }
                case DESTINATION_OUT_TO_IN:
                    encryFlag = DESTINATION_OUT_TO_IN;
                    // 入参解密
                    ReqUtil<DeleteFriendReq> reqUtil = new ReqUtil<>(deleteFriendReq);
                    DeleteFriendReq desReq = reqUtil.decryJsonToReq(deleteFriendReq);
                    result = commonDeleteFriend(desReq);
                    // 返回值加密
                    break;
                default:
                    throw new SystemException("Destination"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
        }catch (SystemException e){
            log.error("UserServiceImpl.deleteFriend SystemException:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }catch (Exception e){
            log.error("UserServiceImpl.deleteFriend Exception:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }

        try {
            result = commonService.encryptForWs(deleteFriendReq.getCommonKey(), encryFlag, result);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
            log.error("UserServiceImpl.deleteFriend encrypt Exception:{}",e.getMessage());
        }
        return result;
    }


    @Override
    @Transactional
    public Result changePasswd(ChangePasswdReq changePasswdReq) {
        Result result;
        try{
            result = commonChangePasswd(changePasswdReq);
        }catch (SystemException e){
            log.error("changePasswd Exception:{}",e.getMessage());
            result = failResult(e);
        }
        catch (Exception e) {
            log.error("changePasswd Exception:{}", e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result queryUser(UserRequest userRequest) {
        String entryFlag = DESTINATION_OUT;
        ReqUtil<UserRequest> jsonUtil = new ReqUtil<>(userRequest);
        userRequest = jsonUtil.jsonReqToDto(userRequest);
        Result result;
        try {
            switch (userRequest.getDestination()){
                case DESTINATION_OUT:
                    result = commonQueryUser(userRequest);
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    userRequest.setDestination(DESTINATION_OUT_TO_IN);
                    userRequest.setUrl(url+UcConstants.URL_QUERYUSERINFO);
                    String param = JSONObject.toJSONString(userRequest);
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
                case DESTINATION_OUT_TO_IN:
                    entryFlag = DESTINATION_OUT_TO_IN;
                    // 入参解密
                    ReqUtil<UserRequest> reqUtil = new ReqUtil<>(userRequest);
                    UserRequest userRequest1 = reqUtil.decryJsonToReq(userRequest);
                    result = commonQueryUser(userRequest1);
                    // 返回值加密
                    break;
                default:
                    throw new SystemException("Destination"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
        }catch (SystemException e){
            log.error("queryUser Exception:{}",e.getMessage());
            result = failResult(e);
        } catch (Exception e){
            log.error("queryUser Exception:{}",e.getMessage());
            result = failResult(e);
        }
        try {
            result = commonService.encrypt(userRequest.getCommonKey(), entryFlag, result);
        }catch (Exception e){
            log.error("基础平台加密异常",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result updateAppCode(AppCodeRequest appCodeRequest) {
        if(ObjectUtils.isEmpty(appCodeRequest) || ObjectUtils.isEmpty(appCodeRequest.getUserId())){
            throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(appCodeRequest.getLoginAppCode()) && ParamCheckUtil.checkVoStrBlank(appCodeRequest.getLogoutAppCode())){
            throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        int n = userDomainService.updateAppCode(appCodeRequest);
        return  buildResult(n);
    }

    @Override
    public Result verify(VerifyReq req) {
        Result result;
        try {
            commonVerify(req);
            result = buildResult(null);
        }catch (SystemException e){
            log.error("UserServiceImpl.verify SystemException:{}",e.getMessage());
            result = failResult(e);
        } catch (Exception e){
            log.error("UserServiceImpl.verify Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result verifyToken(VerifyTokenReq req) {
        Result result;
        try {
            commonVerifyToken(req);
            result = buildResult(null);
        }catch (SystemException e){
            log.error("UserServiceImpl.verifyToken SystemException:{}",e.getMessage());
            result = failResult(e);
        } catch (Exception e){
            log.error("UserServiceImpl.verifyToken Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    @Override
    public void updateUserLogStatus() {
        User user = new User();
        UserExample userExample = new UserExample();
        user.setLoginStatus(DataConfig.LOGIN_STATUS_OUT);
        userMapper.updateByExampleSelective(user,userExample);
    }

    private void commonVerify(VerifyReq req){
        checkVerifyParam(req);
        UserExample example = new UserExample();
        example.createCriteria().andPhoneNumberEqualTo(req.getPhoneNumber()).andIsExistEqualTo(DataConstants.IS_EXIST);
        List<User> users = userMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(users)){
            throw new SystemException(ErrorMessageContants.USER_NO_EXIST_MSG);
        }
        User user = users.get(0);
        judgeAfterSix(req.getAfterSix(),user.getIdNo());
    }


    private void commonVerifyToken(VerifyTokenReq req){
        checkVerifyTokenParam(req);

        // 根据userId从缓存中获取token信息
        String token = getToken(req.getUserId());
        if (!token.equals(req.getToken())){
            throw new SystemException(ErrorMessageContants.TOKEN_ILLEGAL_MSG);
        }

        try{
            String userId = JwtUtils.getClaimByName(req.getToken(),"userId").asString();
            // 校验token是否被别人窜用
            if (!req.getUserId().equals(userId)){
                throw new SystemException(ErrorMessageContants.TOKEN_ILLEGAL_MSG);
            }
        }catch (Exception e){
            log.info(e.getMessage());
            throw new SystemException(ErrorMessageContants.TOKEN_ILLEGAL_MSG);
        }

        int jwtRes = JwtUtils.verifyTokenRes(req.getToken(), req.getUserId());
        if (jwtRes != 0){
            throw new SystemException(ErrorMessageContants.TOKEN_ILLEGAL_MSG);
        }

        UserExample example = new UserExample();
        example.createCriteria().andUserIdEqualTo(req.getUserId()).andIsExistEqualTo(DataConstants.IS_EXIST);
        List<User> users = userMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(users)){
            throw new SystemException(ErrorMessageContants.USER_NO_EXIST_MSG);
        }
    }

    private void checkVerifyParam(VerifyReq verifyReq){
        if (ParamCheckUtil.checkVoStrBlank(verifyReq.getPhoneNumber())) {
            throw new SystemException("PhoneNumber"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(verifyReq.getAfterSix())) {
            throw new SystemException("AfterSix"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkVerifyTokenParam(VerifyTokenReq verifyReq){
        if (ParamCheckUtil.checkVoStrBlank(verifyReq.getToken())) {
            throw new SystemException("token"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(verifyReq.getUserId())) {
            throw new SystemException("userId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void judgeAfterSix(String afterSix,String source){
        if(source.length()>=lastSix){
            if(source.substring(source.length()-lastSix,source.length()).equals(afterSix)){
               return;
            }
        }
        throw new SystemException(ErrorMessageContants.AUTHENTICATION_ERROR);
    }


    private  Result commonQueryUser(UserRequest userRequest)throws Exception {
        if(ObjectUtils.isEmpty(userRequest) || ObjectUtils.isEmpty(userRequest.getQueryParam())){
            return new Result(false, ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        User user = userDomainService.queryUserByqueryParam(userRequest);
        if(user==null){
            throw new SystemException("无此用户");
        }
        UserResp userResp =new UserResp();
        BeanUtils.copyProperties(user,userResp);
        return buildResult(userResp);
    }


    private Result commonChangePasswd(ChangePasswdReq changePasswdReq)throws Exception {
        /**1.0 参数校验**/
        if(ObjectUtils.isEmpty(changePasswdReq) || ObjectUtils.isEmpty(changePasswdReq.getNewPassword())
                || ObjectUtils.isEmpty(changePasswdReq.getRepeatPassword())||
                ObjectUtils.isEmpty(changePasswdReq.getPhoneNumber())){
            throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if(!changePasswdReq.getRepeatPassword().equals(changePasswdReq.getNewPassword())){
            throw new SystemException(PASSWORD_TWICE_INCONSISTENT);
        }

        UserRequest request = new UserRequest();
        request.setPhoneNumber(changePasswdReq.getPhoneNumber());
        User user = userDomainService.selectByCondition(request);
        if(user==null){
            throw new SystemException(NO_USER_BOUND_TEL);
        }
        UserRequest userRequest = new UserRequest();
        userRequest.setUserId(user.getUserId());
        userRequest.setPassword(changePasswdReq.getNewPassword());
        return buildResult(userDomainService.modifyUserInfo(userRequest));
    }



    private Result commonModifyUserInfo(UserRequest userRequest) {
        if(ObjectUtils.isEmpty(userRequest) || ObjectUtils.isEmpty(userRequest.getUserId())){
            throw new SystemException(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        int n = userDomainService.modifyUserInfo(userRequest);
        return  buildResult(n);
    }


    private Result commonDeleteFriend(DeleteFriendReq deleteFriendReq) {
        try {
            /**1.0 参数校验**/
            if(ObjectUtils.isEmpty(deleteFriendReq) || ObjectUtils.isEmpty(deleteFriendReq.getUserId())
                    || ObjectUtils.isEmpty(deleteFriendReq.getFriendUserId())){
                return new Result(false, ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            int n = userDomainService.deleteFriend(deleteFriendReq);
            DeleteFriendReq deleteOpFriend = new DeleteFriendReq();
            deleteOpFriend.setUserId(deleteFriendReq.getFriendUserId());
            deleteOpFriend.setFriendUserId(deleteFriendReq.getUserId());
            int m = userDomainService.deleteFriend(deleteOpFriend);

            WsResultVo wsResultVo = new WsResultVo();
            WsSendVo wsSendVo = new WsSendVo();
            String userId = deleteFriendReq.getUserId();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUserIdEqualTo(userId).andIsExistEqualTo(DataConstants.IS_EXIST);
            List<User> users = userMapper.selectByExample(userExample);
            if (!CollectionUtils.isEmpty(users)){
                User user = users.get(0);
                wsSendVo.setData(JSONObject.toJSONString(user));
                wsSendVo.setFrom(SYSTEM_UC);
                wsSendVo.setBusinessCode("13");
                if (DESTINATION_OUT_TO_IN.equals(deleteFriendReq.getDestination())){
                    wsResultVo.setSendObject(deleteFriendReq.getFriendUserId()+KEY_SPLIT_UNDERLINE+DESTINATION_IN);
                }else {
                    wsResultVo.setSendObject(deleteFriendReq.getFriendUserId()+KEY_SPLIT_UNDERLINE+DESTINATION_OUT);
                }
                wsResultVo.setDestination(deleteFriendReq.getDestination());
            }
            wsResultVo.setResult(JSONObject.toJSONString(wsSendVo));
            return  buildResult(null,null,JSONObject.toJSONString(wsResultVo));
        }catch (Exception e){
            log.error("modifyUserInfo exception:{}",e.getMessage());
            return  failResult(e);
        }
    }

    public String getToken(String userId){
        String token = "";
        StringBuffer sb = new StringBuffer(SYSTEM_UC);
        sb.append(USER_LOGIN_JWT_TOKEN).append(KEY_SPLIT_UNDERLINE).append(userId);
        Object o = redisTemplate.opsForValue().get(sb.toString());
        if (o != null && o instanceof String){
            token = (String) o;
        }
        return token;
    }

}
