package com.matrictime.network.domain.impl;

import com.matrictime.network.api.request.BaseReq;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.base.util.ReqUtil;
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
    public Result encrypt(String userId,String destination, Result res) throws Exception {
        if(UcConstants.DESTINATION_OUT_TO_IN.equals(destination)){
            ReqUtil resUtil = new ReqUtil();
            String resultObj = resUtil.encryJsonToReq(res, getSidByUserId(userId));
            res.setSuccess(true);
            res.setResultObj(resultObj);
            res.setErrorMsg(null);
            res.setErrorCode(null);
        }
        return res;
    }

    @Override
    public String getSidByUserId(String userId) throws Exception {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIdEqualTo(userId).andIsExistEqualTo(DataConstants.IS_EXIST);
        List<User> users = userMapper.selectByExample(userExample);
        if (!CollectionUtils.isEmpty(users)){
            String sid = users.get(0).getSid();
            if (!ParamCheckUtil.checkVoStrBlank(sid)){
                return sid;
            }
        }
        throw new Exception(GET_KEY_FAIL_MSG);
    }
}
