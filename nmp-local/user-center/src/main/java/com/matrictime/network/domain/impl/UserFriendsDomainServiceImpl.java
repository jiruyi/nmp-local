package com.matrictime.network.domain.impl;


import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.dao.mapper.UserFriendsMapper;
import com.matrictime.network.domain.UserFriendsDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserFriendsDomainServiceImpl implements UserFriendsDomainService {

    @Resource
    private UserFriendsMapper userFriendsMapper;

    @Override
    public int cancelUser(UserRequest userRequest) {
        return userFriendsMapper.cancelUser(userRequest);
    }
}
