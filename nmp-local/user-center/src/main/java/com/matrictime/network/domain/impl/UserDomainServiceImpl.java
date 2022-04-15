package com.matrictime.network.domain.impl;

import com.matrictime.network.api.request.DeleteFriendReq;
import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.dao.mapper.UserFriendMapper;
import com.matrictime.network.dao.mapper.UserGroupMapper;
import com.matrictime.network.dao.mapper.UserMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.domain.UserDomainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/31 0031 16:23
 * @desc
 */
@Service
@Slf4j
public class UserDomainServiceImpl implements UserDomainService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserFriendMapper userFriendMapper;
    @Autowired
    private UserGroupMapper userGroupMapper;


    /**
     * @param [userRequest]
     * @return int
     * @title modifyUserInfo
     * @description
     * @author jiruyi
     * @create 2022/4/6 0006 10:06
     */
    @Override
    public int modifyUserInfo(UserRequest userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        UserExample example = new UserExample();
        example.createCriteria().andUserIdEqualTo(userRequest.getUserId());
        return userMapper.updateByExampleSelective(user, example);
    }

    /**
     * @param [deleteFriendReq]
     * @return int
     * @title deleteFriend
     * @description
     * @author jiruyi
     * @create 2022/4/12 0012 14:00
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteFriend(DeleteFriendReq deleteFriendReq) {
        UserFriendExample userFriendExample = new UserFriendExample();
        userFriendExample.createCriteria().andUserIdEqualTo(deleteFriendReq.getUserId())
                .andFriendUserIdEqualTo(deleteFriendReq.getFriendUserId());
        //1.0删除用户好友
        UserFriend userFriend = UserFriend.builder().isExist(false).build();
        int n = userFriendMapper.updateByExampleSelective(userFriend, userFriendExample);
        //2.0删除好友群组
        UserGroupExample userGroupExample = new UserGroupExample();
        userGroupExample.createCriteria().andUserIdEqualTo(deleteFriendReq.getUserId())
                .andGroupIdEqualTo(deleteFriendReq.getGroupId());
        UserGroup userGroup = UserGroup.builder().isExist(false).build();
        int m = userGroupMapper.updateByExampleSelective(userGroup, userGroupExample);
        if (n > 0 && m > 0) {
            return NumberUtils.INTEGER_ONE;
        } else {
            return NumberUtils.INTEGER_ZERO;
        }
    }

    @Override
    public User selectByCondition(UserRequest userRequest) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if(userRequest.getPhoneNumber()!=null){
            criteria.andPhoneNumberEqualTo(userRequest.getPhoneNumber());
        }
        if(userRequest.getUserId()!=null){
            criteria.andUserIdEqualTo(userRequest.getUserId());
        }
        if(userRequest.getLoginAccount()!=null){
            criteria.andLoginAccountEqualTo(userRequest.getLoginAccount());
        }
        criteria.andIsExistEqualTo(true);
        return userMapper.selectByExample(userExample).get(0);
    }
}
