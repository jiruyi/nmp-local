package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.api.request.DeleteFriendReq;
import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.domain.UserDomainService;
import com.matrictime.network.dao.mapper.GroupInfoMapper;
import com.matrictime.network.dao.mapper.UserFriendMapper;
import com.matrictime.network.dao.mapper.UserGroupMapper;
import com.matrictime.network.dao.mapper.UserMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
    @Autowired
    private GroupInfoMapper groupInfoMapper;


    /**
     * @param userRequest
     * @return int
     * @title modifyUserInfo
     * @description
     * @author jiruyi
     * @create 2022/4/6 0006 10:06
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int modifyUserInfo(UserRequest userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        UserExample example = new UserExample();
        example.createCriteria().andUserIdEqualTo(userRequest.getUserId());
        return userMapper.updateByExampleSelective(user, example);
    }

    /**
     * @param deleteFriendReq
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
        //2.0删除用户好友群组中该好友
        GroupInfoExample groupInfoExample = new GroupInfoExample();
        groupInfoExample.createCriteria().andOwnerEqualTo(deleteFriendReq.getUserId()).andIsExistEqualTo(DataConstants.IS_EXIST);
        List<GroupInfo> groupInfos = groupInfoMapper.selectByExample(groupInfoExample);
        if (!CollectionUtils.isEmpty(groupInfos)) {
            for (GroupInfo groupInfo : groupInfos) {
                UserGroupExample userGroupExample = new UserGroupExample();
                userGroupExample.createCriteria().andUserIdEqualTo(deleteFriendReq.getFriendUserId())
                        .andGroupIdEqualTo(String.valueOf(groupInfo.getGroupId())).andIsExistEqualTo(DataConstants.IS_EXIST);
                UserGroup userGroup = UserGroup.builder().isExist(false).build();
                userGroupMapper.updateByExampleSelective(userGroup, userGroupExample);
            }
        }
        if (n > 0) {
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
        List<User> userList = userMapper.selectByExample(userExample);
        if(!CollectionUtils.isEmpty(userList)){
            return userList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public User queryUserByqueryParam(UserRequest userRequest) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if(ParamCheckUtil.checkVoStrBlank(userRequest.getQueryParam())){
            throw new SystemException("参数缺失");
        }
        userExample.or().andUserIdEqualTo(userRequest.getQueryParam()).andIsExistEqualTo(true);
        userExample.or().andPhoneNumberEqualTo(userRequest.getQueryParam()).andIsExistEqualTo(true);
        userExample.or().andLoginAccountEqualTo(userRequest.getQueryParam()).andIsExistEqualTo(true);
        List<User> userList = userMapper.selectByExample(userExample);
        if(!CollectionUtils.isEmpty(userList)){
            return userList.get(0);
        }else {
            return null;
        }
    }
}
