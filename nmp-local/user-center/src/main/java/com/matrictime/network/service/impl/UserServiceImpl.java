package com.matrictime.network.service.impl;

import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.domain.UserDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
