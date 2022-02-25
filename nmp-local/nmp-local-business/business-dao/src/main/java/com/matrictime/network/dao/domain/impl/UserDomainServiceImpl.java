package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.enums.LoginTypeEnum;
import com.matrictime.network.dao.domain.UserDomainService;
import com.matrictime.network.dao.mapper.NmplUserMapper;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.dao.model.NmplUserExample;
import com.matrictime.network.request.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/2/24 0024 13:43
 * @desc
 */
@Service
@Slf4j
public class UserDomainServiceImpl implements UserDomainService {

    @Autowired
    private NmplUserMapper userMapper;
    
    /**
      * @title getUserByParamter
      * @param [loginRequest]
      * @return java.util.List<com.matrictime.network.dao.model.NmplUser>
      * @description 
      * @author jiruyi
      * @create 2022/2/24 0024 13:43
      */
    @Override
    public List<NmplUser> getUserByParamter(LoginRequest loginRequest) {
        NmplUserExample userExample = new NmplUserExample();
        //用户名查询
        if (loginRequest.getType().equals(LoginTypeEnum.PASSWORD.getCode())){
            userExample.createCriteria().andLoginAccountEqualTo(loginRequest.getLoginAccount());
        }
        //手机号查询
        if (loginRequest.getType().equals(LoginTypeEnum.MOBILEPHONE.getCode())){
            userExample.createCriteria().andPhoneNumberEqualTo(loginRequest.getPhone());
        }
        List<NmplUser> userList =  userMapper.selectByExample(userExample);
        log.info("UserDomainService.getUserByParamter() result is :{}",userList);
        return userList;
    }
}
