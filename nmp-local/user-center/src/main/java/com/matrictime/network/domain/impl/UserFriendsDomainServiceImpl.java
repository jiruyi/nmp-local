package com.matrictime.network.domain.impl;


import com.matrictime.network.api.modelVo.UserFriendVo;
import com.matrictime.network.api.request.AddUserRequestReq;
import com.matrictime.network.api.request.UserFriendReq;
import com.matrictime.network.dao.mapper.ext.AddUserRequestExtMapper;
import com.matrictime.network.dao.mapper.ext.UserFriendExtMapper;
import com.matrictime.network.domain.UserFriendsDomainService;
import com.matrictime.network.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UserFriendsDomainServiceImpl implements UserFriendsDomainService {

    @Resource
    private UserFriendExtMapper userFriendExtMapper;

    @Resource
    private AddUserRequestExtMapper addUserRequestExtMapper;

    @Override
    public List<UserFriendVo> selectUserFriend(UserFriendReq userFriendReq) {
        return userFriendExtMapper.selectUserFriend(userFriendReq);
    }

    @Override
    public int addFriends(AddUserRequestReq addUserRequestReq) {
        addUserRequestReq.setRequestId(SnowFlake.nextId_String());
        return addUserRequestExtMapper.addFriends(addUserRequestReq);
    }
}



























