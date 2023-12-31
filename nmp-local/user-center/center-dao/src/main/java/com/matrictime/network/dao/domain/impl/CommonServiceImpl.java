package com.matrictime.network.dao.domain.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.matrictime.network.api.modelVo.WsResultVo;
import com.matrictime.network.api.request.LoginReq;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.base.util.ReqUtil;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.domain.CommonService;
import com.matrictime.network.dao.mapper.UserMapper;
import com.matrictime.network.dao.model.User;
import com.matrictime.network.dao.model.UserExample;
import com.matrictime.network.exception.ErrorCode;
import com.matrictime.network.model.Result;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.matrictime.network.constant.DataConstants.KEY_SPLIT_UNDERLINE;
import static com.matrictime.network.exception.ErrorMessageContants.GET_KEY_FAIL_MSG;

@Service
@Slf4j
public class CommonServiceImpl extends SystemBaseService implements CommonService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public Result encrypt(String condition,String destination, Result res) throws Exception {
        if(UcConstants.DESTINATION_OUT_TO_IN.equals(destination)){
            // if errorCode is system error, don't encrypt
            if (ErrorCode.SYSTEM_ERROR.equals(res.getErrorCode())) {
                return res;
            }
            ReqUtil resUtil = new ReqUtil();
            log.info("通用接口开始加密了歪：{},{},{}",condition,destination,JSONObject.toJSONString(res));
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
            // if errorCode is system error, don't encrypt
            if (ErrorCode.SYSTEM_ERROR.equals(res.getErrorCode())) {
                return res;
            }
            ReqUtil resUtil = new ReqUtil();
            log.info("encryptForWs errormsg:{}",res.getErrorMsg());
            if (res.isSuccess()){
                WsResultVo wsResultVo = JSONObject.parseObject(res.getErrorMsg(), new TypeReference<WsResultVo>() {});
                String result = wsResultVo.getResult();
                wsResultVo.setDestination(UcConstants.DESTINATION_IN);
                String[] strings = wsResultVo.getSendObject().split(KEY_SPLIT_UNDERLINE);
                String encryJsonToReq = resUtil.encryJsonToReq(result, getSidByCondition(strings[0]));
                wsResultVo.setResult(encryJsonToReq);
                res.setErrorMsg(null);
                String sid = getSidByCondition(condition);
                String resultObj = resUtil.encryJsonToReq(res, sid);
                res.setSuccess(true);
                res.setResultObj(resultObj);
                res.setErrorMsg(JSONObject.toJSONString(wsResultVo));
                res.setErrorCode(null);
            }else {
                res.setErrorMsg(null);
                String sid = getSidByCondition(condition);
                String resultObj = resUtil.encryJsonToReq(res, sid);
                res.setSuccess(true);
                res.setResultObj(resultObj);
                res.setErrorCode(null);
            }

        }
        return res;
    }

    @Override
    public Result encryptForLogin(LoginReq req, String destination, Result res) throws Exception {
        log.info("登录开始加密了歪：{},{}",JSONObject.toJSONString(req),JSONObject.toJSONString(res));
        if(UcConstants.DESTINATION_OUT_TO_IN.equals(destination)){
            if (ErrorCode.SYSTEM_ERROR.equals(res.getErrorCode())) {
                return res;
            }
            String extendMsg = res.getExtendMsg();
            res.setExtendMsg(null);
            if (StringUtils.isBlank(extendMsg)){
                return res;
            }
            JSONObject jsonObject = JSONObject.parseObject(extendMsg);
            String sid = jsonObject.getString("sid");
            jsonObject.remove("sid");
            ReqUtil resUtil = new ReqUtil();
            log.info("登录开始加密了：{},{}",JSONObject.toJSONString(req),JSONObject.toJSONString(res));
            String resultObj = resUtil.encryJsonToReq(res, sid);
            res.setSuccess(true);
            res.setResultObj(resultObj);
            res.setErrorMsg(null);
            res.setErrorCode(null);
            res.setExtendMsg(jsonObject.toJSONString());
        }
        return res;
    }

    @Override
    public Result encryptForRegister(String sid,String destination, Result res) throws Exception {
        log.info("注册开始加密了歪：{},{},{}",sid,destination,JSONObject.toJSONString(res));
        if(UcConstants.DESTINATION_OUT_TO_IN_SYN.equals(destination)){
            if (ErrorCode.SYSTEM_ERROR.equals(res.getErrorCode())) {
                return res;
            }
            ReqUtil resUtil = new ReqUtil();
            log.info("注册开始加密了：{},{},{}",sid,destination,JSONObject.toJSONString(res));
            String resultObj = resUtil.encryJsonToReq(res, sid);
            res.setSuccess(true);
            res.setResultObj(resultObj);
            res.setErrorMsg(null);
            res.setErrorCode(null);
        }
        return res;
    }


    public String getSidByCondition(String condition) throws Exception {

        if (ParamCheckUtil.checkVoStrBlank(condition)){
            throw new Exception(GET_KEY_FAIL_MSG);
        }

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
