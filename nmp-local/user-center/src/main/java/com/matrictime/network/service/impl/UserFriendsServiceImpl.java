package com.matrictime.network.service.impl;

import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.domain.UserFriendsDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.UserFriendsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserFriendsServiceImpl implements UserFriendsService {

    @Resource
    private UserFriendsDomainService userFriendsDomainService;

    @Override
    public Result<Integer> cancelUser(UserRequest userRequest) {
        Result<Integer> result = new Result<>();
        try {
            result.setResultObj(userFriendsDomainService.cancelUser(userRequest));
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }
}
