package com.matrictime.network.domain.impl;


import com.matrictime.network.api.modelVo.UserFriendVo;
import com.matrictime.network.api.request.UserFriendReq;
import com.matrictime.network.dao.mapper.ext.UserFriendExtMapper;
import com.matrictime.network.domain.UserFriendsDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UserFriendsDomainServiceImpl implements UserFriendsDomainService {

    @Resource
    private UserFriendExtMapper userFriendExtMapper;

    @Override
    public List<UserFriendVo> selectUserFriend(UserFriendReq userFriendReq) {
        return userFriendExtMapper.selectUserFriend(userFriendReq);
    }
}
