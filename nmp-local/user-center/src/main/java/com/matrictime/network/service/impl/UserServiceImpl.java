package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jzsg.bussiness.JServiceImpl;
import com.jzsg.bussiness.model.ReqModel;
import com.jzsg.bussiness.model.ResModel;
import com.matrictime.network.api.modelVo.UserVo;
import com.matrictime.network.api.request.*;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.UserMapper;
import com.matrictime.network.dao.model.User;
import com.matrictime.network.dao.model.UserExample;
import com.matrictime.network.domain.UserDomainService;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.UserService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.util.List;
import java.util.regex.Pattern;

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

    @Autowired(required = false)
    private UserMapper userMapper;

    @Value("${app.innerUrl}")
    private String url;

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "0?(13|14|15|18)[0-9]{9}";


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
    public Result modifyUserInfo(UserRequest userRequest) {
        try {
            int n = userDomainService.modifyUserInfo(userRequest);
            return  buildResult(n);
        }catch (Exception e){
            log.error("modifyUserInfo exception:{}",e.getMessage());
            return  failResult(e);
        }
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
    public Result deleteFriend(DeleteFriendReq deleteFriendReq) {
        try {
            int n = userDomainService.deleteFriend(deleteFriendReq);
            return  buildResult(n);
        }catch (Exception e){
            log.error("modifyUserInfo exception:{}",e.getMessage());
            return  failResult(e);
        }
    }


    @Override
    public Result changePasswd(ChangePasswdReq changePasswdReq) {
        if(changePasswdReq.getRepeatPassword().equals(changePasswdReq.getNewPassword())){
            throw new SystemException("两次输入密码不一致");
        }
        UserRequest request = new UserRequest();
        request.setPhoneNumber(changePasswdReq.getPhoneNumber());
        User user = userDomainService.selectByCondition(request);
        if(user==null){
            throw new SystemException("无用户与此电话号绑定");
        }
        UserRequest userRequest = new UserRequest();
        userRequest.setUserId(user.getUserId());
        userRequest.setPassword(changePasswdReq.getNewPassword());
        return buildResult(userDomainService.modifyUserInfo(userRequest));
    }

    @Override
    public Result queryUser(UserRequest userRequest) {
       String queryParam = userRequest.getQueryParam();
       if(isMobile(queryParam)){
           userRequest.setPhoneNumber(queryParam);
       }else {
           userRequest.setLoginAccount(queryParam);
       }
       User user = userDomainService.selectByCondition(userRequest);
       if(user==null){
            throw new SystemException("无此用户");
       }
       UserVo userVo =new UserVo();
       BeanUtils.copyProperties(user,userVo);
       return buildResult(userVo);
    }

    @Override
    public Result verify(VerifyReq req) {
        Result result;
        try {
            checkVerifyParam(req);

            switch (req.getDestination()){
                case UcConstants.DESTINATION_OUT:
                    commonVerify(req);
                    break;
                case UcConstants.DESTINATION_IN:
                    ReqModel reqModel = new ReqModel();
                    req.setDestination(UcConstants.DESTINATION_OUT_TO_IN);
                    req.setUrl(url+UcConstants.URL_VERIFY);
                    String param = JSONObject.toJSONString(req);
                    log.info("非密区向密区发送请求参数param:{}",param);
                    reqModel.setParam(param);
                    ResModel resModel = JServiceImpl.syncSendMsg(reqModel);
                    log.info("非密区接收密区返回值ResModel:{}",JSONObject.toJSONString(resModel));
                    return buildResult(null);

                case UcConstants.DESTINATION_OUT_TO_IN:
                    // 入参解密

                    VerifyReq desReq = new VerifyReq();
                    commonVerify(desReq);
                    // 返回值加密

                    return buildResult(null);
                default:
                    throw new SystemException("Destination"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);

            }

            result = buildResult(null);
        }catch (SystemException e){
            log.error("UserServiceImpl.verify Exception:{}",e.getMessage());
            result = failResult(e);
        } catch (Exception e){
            log.error("UserServiceImpl.verify Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    private void commonVerify(VerifyReq req){
        UserExample example = new UserExample();
        example.createCriteria().andPhoneNumberEqualTo(req.getPhoneNumber()).andIsExistEqualTo(DataConstants.IS_EXIST);
        List<User> users = userMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(users)){
            throw new SystemException(ErrorMessageContants.USERNAME_NO_EXIST_MSG);
        }
        User user = users.get(0);
        judgeAfterSix(req.getAfterSix(),user.getIdNo());
    }

    private void checkVerifyParam(VerifyReq verifyReq){
        if (ParamCheckUtil.checkVoStrBlank(verifyReq.getPhoneNumber())) {
            throw new SystemException("PhoneNumber"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(verifyReq.getAfterSix())) {
            throw new SystemException("AfterSix"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void judgeAfterSix(String afterSix,String source){
        if(source.length()>=6){
            if(source.substring(source.length()-6,source.length()).equals(afterSix)){
               return;
            }
        }
        throw new SystemException("身份验证错误");
    }
}
