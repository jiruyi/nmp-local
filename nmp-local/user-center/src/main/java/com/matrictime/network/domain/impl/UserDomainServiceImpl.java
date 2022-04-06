package com.matrictime.network.domain.impl;

import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.dao.mapper.UserMapper;
import com.matrictime.network.dao.model.User;
import com.matrictime.network.dao.model.UserExample;
import com.matrictime.network.domain.UserDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
      * @title modifyUserInfo
      * @param [userRequest]
      * @return int
      * @description
      * @author jiruyi
      * @create 2022/4/6 0006 10:06
      */
    @Override
    public int modifyUserInfo(UserRequest userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest,user);
        UserExample example =  new UserExample();
        example.createCriteria().andUserIdEqualTo(userRequest.getUserId());
        return  userMapper.updateByExampleSelective(user,example);
    }
}
