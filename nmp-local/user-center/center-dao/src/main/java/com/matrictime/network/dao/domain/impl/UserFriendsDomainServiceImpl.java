package com.matrictime.network.dao.domain.impl;


import com.matrictime.network.api.modelVo.*;
import com.matrictime.network.api.request.AddUserRequestReq;
import com.matrictime.network.api.request.GroupReq;
import com.matrictime.network.api.request.UserFriendReq;
import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.dao.domain.UserFriendsDomainService;
import com.matrictime.network.dao.mapper.UserFriendMapper;
import com.matrictime.network.dao.mapper.ext.AddUserRequestExtMapper;
import com.matrictime.network.dao.mapper.ext.UserFriendExtMapper;
import com.matrictime.network.dao.model.UserFriend;
import com.matrictime.network.dao.model.UserFriendExample;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UserFriendsDomainServiceImpl implements UserFriendsDomainService {

    @Resource
    private UserFriendExtMapper userFriendExtMapper;
    @Resource
    private UserFriendMapper userFriendMapper;

    @Resource
    private AddUserRequestExtMapper addUserRequestExtMapper;

    @Override
    public List<UserFriendVo> selectUserFriend(UserFriendReq userFriendReq) {
        return userFriendExtMapper.selectUserFriend(userFriendReq);
    }

    @Override
    public int addFriends(AddUserRequestReq addUserRequestReq) {
        return addUserRequestExtMapper.addFriends(addUserRequestReq);
    }

    @Override
    public int insertFriend(UserFriendReq userFriendReq) {
        return userFriendExtMapper.insertFriend(userFriendReq);
    }

    @Override
    public UserVo selectUserInfo(UserRequest userRequest) {
        return userFriendExtMapper.selectUserInfo(userRequest);
    }

    @Override
    public List<AddUserRequestVo> getAddUserInfo(AddUserRequestReq addUserRequestReq) {
        return addUserRequestExtMapper.getAddUserInfo(addUserRequestReq);
    }

    @Override
    public GroupVo selectGroupInfo(GroupReq groupReq) {
        return userFriendExtMapper.selectGroupInfo(groupReq);
    }

    @Override
    public int update(AddUserRequestReq addUserRequestReq) {
        return addUserRequestExtMapper.update(addUserRequestReq);
    }

    @Override
    public AddRequestVo selectGroupId(AddUserRequestReq addUserRequestReq) {
        return addUserRequestExtMapper.selectGroupId(addUserRequestReq);
    }


    @Override
    public Integer modifyUserFriend(UserFriendReq userFriendReq) {
        if(ParamCheckUtil.checkVoStrBlank(userFriendReq.getUserId())
                ||ParamCheckUtil.checkVoStrBlank(userFriendReq.getFriendUserId())
                || ParamCheckUtil.checkVoStrBlank(userFriendReq.getRemarkName())){
            throw new SystemException("参数缺失");
        }
        UserFriend userFriend = new UserFriend();
        BeanUtils.copyProperties(userFriendReq,userFriend);
        UserFriendExample userFriendExample = new UserFriendExample();
        userFriendExample.createCriteria().andUserIdEqualTo(userFriendReq.getUserId()).andFriendUserIdEqualTo(userFriendReq.getFriendUserId())
                .andIsExistEqualTo(true);
        return userFriendMapper.updateByExampleSelective(userFriend,userFriendExample) ;
    }
}



























