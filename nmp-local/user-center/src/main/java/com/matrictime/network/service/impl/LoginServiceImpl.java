package com.matrictime.network.service.impl;

import com.matrictime.network.api.request.BindReq;
import com.matrictime.network.api.request.LoginReq;
import com.matrictime.network.api.request.LogoutReq;
import com.matrictime.network.api.request.RegisterReq;
import com.matrictime.network.api.response.LoginResp;
import com.matrictime.network.api.response.RegisterResp;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.config.DataConfig;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.UserMapper;
import com.matrictime.network.dao.model.User;
import com.matrictime.network.dao.model.UserExample;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.LoginService;
import com.matrictime.network.util.ParamCheckUtil;
import com.matrictime.network.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.matrictime.network.config.DataConfig.LOGIN_STATUS_IN;

@Service
@Slf4j
public class LoginServiceImpl extends SystemBaseService implements LoginService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public Result<RegisterResp> register(RegisterReq req) {
        Result result;
        try {
            RegisterResp resp = new RegisterResp();
            checkRegisterParam(req);

            // 判断用户是否存在
            boolean userExist = checkUserExist(req);
            if(userExist){
                throw new SystemException(ErrorMessageContants.USER_IS_EXIST_MSG);
            }

            User user = new User();
            String userId = SnowFlake.nextId_String();
            user.setUserId(userId);
            user.setLoginAccount(req.getLoginAccount());
            user.setPassword(req.getPassword());
            user.setEmail(req.getEmail());
            user.setPhoneNumber(req.getPhoneNumber());
            user.setUserType(req.getUserType());
            user.setIdType(req.getIdType());
            user.setIdNo(req.getIdNo());
            userMapper.insertSelective(user);

            resp.setUserId(userId);
            result = buildResult(resp);
        }catch (Exception e){
            log.error("LoginServiceImpl.register Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<LoginResp> login(LoginReq req) {
        Result result;
        try {
            LoginResp resp = new LoginResp();
            checkLoginParam(req);

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
                user.setDeviceId(req.getDeviceId());
                user.setDeviceIp(req.getDeviceIp());
                user.setLoginStatus(LOGIN_STATUS_IN);
                user.setLoginAppCode(req.getLoginAppCode());
                userMapper.updateByPrimaryKeySelective(user);
                resp.setUserInfo(user.toString());
            }

            result = buildResult(resp);
        }catch (Exception e){
            log.error("LoginServiceImpl.login Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result logout(LogoutReq req) {
        Result result;
        try {
            checkLogoutParam(req);

            User user = new User();
            user.setUserId(req.getUserId());
            user.setLoginStatus(DataConfig.LOGIN_STATUS_OUT);
            userMapper.updateByPrimaryKeySelective(user);

            result = buildResult(null);
        }catch (Exception e){
            log.error("LoginServiceImpl.logout Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result bind(BindReq req) {
        Result result;
        try {
            checkBindParam(req);

            switch (req.getOprType()){
                case DataConfig.OPR_TYPE_BIND:

                    break;
                case DataConfig.OPR_TYPE_UNBIND:
                    break;
                default:
                    throw new SystemException("OprType"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }

            result = buildResult(null);
        }catch (Exception e){
            log.error("LoginServiceImpl.bind Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    private boolean checkUserForBind(BindReq req){
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
        return false;
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
        criteria1.andLoginAccountEqualTo(req.getLoginAccount());
        UserExample.Criteria criteria2 = userExample.createCriteria();
        criteria2.andEmailEqualTo(req.getEmail());
        userExample.or(criteria2);
        UserExample.Criteria criteria3 = userExample.createCriteria();
        criteria3.andPhoneNumberEqualTo(req.getPhoneNumber());
        userExample.or(criteria3);
        UserExample.Criteria criteria4 = userExample.createCriteria();
        criteria4.andIdTypeEqualTo(req.getIdType()).andIdNoEqualTo(req.getIdNo());
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
        if(ParamCheckUtil.checkVoStrBlank(req.getLoginAccount()) || ParamCheckUtil.checkVoStrBlank(req.getUserId())){
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
    }
}
