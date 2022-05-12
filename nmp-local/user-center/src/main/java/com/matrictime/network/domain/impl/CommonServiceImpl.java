package com.matrictime.network.domain.impl;

import com.matrictime.network.api.request.BaseReq;
import com.matrictime.network.api.request.LoginReq;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.base.util.ReqUtil;
import com.matrictime.network.config.DataConfig;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.UserMapper;
import com.matrictime.network.dao.model.User;
import com.matrictime.network.dao.model.UserExample;
import com.matrictime.network.domain.CommonService;
import com.matrictime.network.model.Result;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.matrictime.network.exception.ErrorMessageContants.GET_KEY_FAIL_MSG;

@Service
@Slf4j
public class CommonServiceImpl extends SystemBaseService implements CommonService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public Result encrypt(String condition,String destination, Result res) throws Exception {
        if(UcConstants.DESTINATION_OUT_TO_IN.equals(destination)){
            ReqUtil resUtil = new ReqUtil();
            String resultObj = resUtil.encryJsonToReq(res, getSidByCondition(condition));
            res.setSuccess(true);
            res.setResultObj(resultObj);
            res.setErrorMsg(null);
            res.setErrorCode(null);
        }
        return res;
    }

    @Override
    public String encryptToString(String condition, String destination, Object object) throws Exception {
        if(UcConstants.DESTINATION_OUT_TO_IN.equals(destination)){
            ReqUtil resUtil = new ReqUtil();
            String resultObj = resUtil.encryJsonToReq(object, getSidByCondition(condition));
            return resultObj;
        }
        return null;
    }

    @Override
    public Result encryptForWs(String condition, String destination, Result res) throws Exception {
        if(UcConstants.DESTINATION_OUT_TO_IN.equals(destination)){
            ReqUtil resUtil = new ReqUtil();
            String errorMsg = res.getErrorMsg();
            res.setErrorMsg(null);
            String resultObj = resUtil.encryJsonToReq(res, getSidByCondition(condition));
            res.setSuccess(true);
            res.setResultObj(resultObj);
            res.setErrorMsg(errorMsg);
            res.setErrorCode(null);
        }
        return res;
    }

    @Override
    public Result encryptForLogin(LoginReq req, Result res) throws Exception {
        if(UcConstants.DESTINATION_OUT_TO_IN.equals(req.getDestination())){
            String userId = res.getErrorMsg();
            ReqUtil resUtil = new ReqUtil();
            String resultObj = resUtil.encryJsonToReq(res, getSidByCondition(req.getCommonKey()));
            res.setSuccess(true);
            res.setResultObj(resultObj);
            res.setErrorMsg(userId);
            res.setErrorCode(null);
        }
        return res;
    }

    public String getSidByCondition(String condition) throws Exception {
        UserExample userExample1 = new UserExample();
        userExample1.createCriteria().andUserIdEqualTo(condition).andIsExistEqualTo(DataConstants.IS_EXIST);
        List<User> users1 = userMapper.selectByExample(userExample1);
        if (!CollectionUtils.isEmpty(users1)){
            String sid = users1.get(0).getSid();
            if (!ParamCheckUtil.checkVoStrBlank(sid)){
                return sid;
            }
        }

        UserExample userExample2 = new UserExample();
        userExample2.createCriteria().andLoginAccountEqualTo(condition).andIsExistEqualTo(DataConstants.IS_EXIST);
        List<User> users2 = userMapper.selectByExample(userExample2);
        if (!CollectionUtils.isEmpty(users2)){
            String sid = users2.get(0).getSid();
            if (!ParamCheckUtil.checkVoStrBlank(sid)){
                return sid;
            }
        }

        UserExample userExample3 = new UserExample();
        userExample3.createCriteria().andPhoneNumberEqualTo(condition).andIsExistEqualTo(DataConstants.IS_EXIST);
        List<User> users3 = userMapper.selectByExample(userExample3);
        if (!CollectionUtils.isEmpty(users3)){
            String sid = users3.get(0).getSid();
            if (!ParamCheckUtil.checkVoStrBlank(sid)){
                return sid;
            }
        }

        throw new Exception(GET_KEY_FAIL_MSG);
    }
}
