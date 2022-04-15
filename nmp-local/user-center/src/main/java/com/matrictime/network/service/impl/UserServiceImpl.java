package com.matrictime.network.service.impl;

import com.matrictime.network.api.modelVo.UserVo;
import com.matrictime.network.api.request.ChangePasswdReq;
import com.matrictime.network.api.request.DeleteFriendReq;
import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.model.User;
import com.matrictime.network.domain.UserDomainService;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
      * @param [userRequest]
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
      * @param [deleteFriendReq]
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
}
