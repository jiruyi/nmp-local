package com.matrictime.network.domain.impl;

import com.matrictime.network.api.modelVo.UserGroupVo;
import com.matrictime.network.api.request.UserGroupReq;
import com.matrictime.network.api.response.UserGroupResp;
import com.matrictime.network.dao.mapper.UserGroupMapper;
import com.matrictime.network.dao.mapper.ext.UserGroupExtMapper;
import com.matrictime.network.dao.model.Group;
import com.matrictime.network.dao.model.UserGroup;
import com.matrictime.network.domain.UserGroupDomianService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupDomianServiceImpl implements UserGroupDomianService {

    @Autowired
    UserGroupExtMapper userGroupExtMapper;
    @Autowired
    UserGroupMapper userGroupMapper;

    @Override
    public Integer createUserGroup(UserGroupReq userGroupReq) {
        UserGroup userGroup = new UserGroup();
        BeanUtils.copyProperties(userGroupReq,userGroup);
        return userGroupMapper.insert(userGroup);
    }

    @Override
    public Integer deleteUserGroup(UserGroupReq userGroupReq)
    {
        userGroupReq.setIsExist(false);
        return userGroupExtMapper.updateByUserIdAndGroupId(userGroupReq);
    }

    @Override
    public Integer modifyUserGroup(UserGroupReq userGroupReq) {
        return userGroupExtMapper.updateByUserIdAndGroupId(userGroupReq);
    }

    @Override
    public List<UserGroupVo>  queryUserGroup(UserGroupReq userGroupReq) {
        return userGroupExtMapper.selectByCondition(userGroupReq);
    }
}
