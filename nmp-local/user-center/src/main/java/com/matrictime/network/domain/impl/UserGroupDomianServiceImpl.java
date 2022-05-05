package com.matrictime.network.domain.impl;

import com.matrictime.network.api.modelVo.UserGroupVo;
import com.matrictime.network.api.request.UserGroupReq;
import com.matrictime.network.api.response.UserGroupResp;
import com.matrictime.network.dao.mapper.UserFriendMapper;
import com.matrictime.network.dao.mapper.UserGroupMapper;
import com.matrictime.network.dao.mapper.ext.UserGroupExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.domain.UserGroupDomianService;
import com.matrictime.network.exception.SystemException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserGroupDomianServiceImpl implements UserGroupDomianService {

    @Resource
    UserGroupExtMapper userGroupExtMapper;
    @Resource
    UserGroupMapper userGroupMapper;
    @Resource
    UserFriendMapper userFriendMapper;
    @Override
    public Integer createUserGroup(UserGroupReq userGroupReq) {
        if(userGroupReq.getGroupId()==null||userGroupReq.getUserId()==null){
            throw new SystemException("缺少参数");
        }
        UserGroup userGroup = new UserGroup();
        BeanUtils.copyProperties(userGroupReq,userGroup);
        return userGroupMapper.insertSelective(userGroup);
    }

    @Override
    public Integer deleteUserGroup(UserGroupReq userGroupReq)
    {
        if(userGroupReq.getGroupId()==null||userGroupReq.getUserId()==null){
            throw new SystemException("缺少参数");
        }
        userGroupReq.setIsExist(false);
        return userGroupExtMapper.updateByUserIdAndGroupId(userGroupReq);
    }

    @Override
    public Integer modifyUserGroup(UserGroupReq userGroupReq) {
        if(userGroupReq.getGroupId()==null||userGroupReq.getUserId()==null||userGroupReq.getTargetGroupId()==null){
            throw new SystemException("缺少参数");
        }
        return userGroupExtMapper.updateByUserIdAndGroupId(userGroupReq);
    }

    @Override
    public List<UserGroupVo>  queryUserGroup(UserGroupReq userGroupReq) {
        if(userGroupReq.getGroupId()==null && userGroupReq.getUserId()==null){
            throw new SystemException("缺少参数");
        }
        return userGroupExtMapper.selectByCondition(userGroupReq);
    }

    @Override
    public Map<String, List<UserGroupVo>> queryUserGroupByGroupIds(List<String>groupIds,String owner) {
        /**
         * 入参校验
         */
        if(CollectionUtils.isEmpty(groupIds)||owner==null){
            return new HashMap<>();
        }
        UserFriendExample userFriendExample = new UserFriendExample();
        userFriendExample.createCriteria().andUserIdEqualTo(owner).andIsExistEqualTo(true);
        List<UserFriend> userFriendList = userFriendMapper.selectByExample(userFriendExample);
        Map<String,UserFriend> userFriendMap = new HashMap<>();
        for (UserFriend userFriend : userFriendList) {
            userFriendMap.put(userFriend.getFriendUserId(),userFriend);
        }
        List<UserGroupVo> userGroupVos = userGroupExtMapper.selectByGroupIds(groupIds);
        Map<String, List<UserGroupVo>> result = new HashMap<>();
        for (UserGroupVo userGroupVo : userGroupVos) {
            if(userFriendMap.get(userGroupVo.getUserId())!=null){
                userGroupVo.setRemarkName(userFriendMap.get(userGroupVo.getUserId()).getRemarkName());
            }
            if(result.get(userGroupVo.getGroupId())==null){
                result.put(userGroupVo.getGroupId(),new ArrayList<>());
            }
            result.get(userGroupVo.getGroupId()).add(userGroupVo);
        }
        return result;
    }
}
