package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.enums.LoginTypeEnum;
import com.matrictime.network.base.util.DateUtils;
import com.matrictime.network.dao.domain.UserDomainService;
import com.matrictime.network.dao.mapper.NmplLoginDetailMapper;
import com.matrictime.network.dao.mapper.NmplUserMapper;
import com.matrictime.network.dao.model.NmplLoginDetail;
import com.matrictime.network.dao.model.NmplLoginDetailExample;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.dao.model.NmplUserExample;
import com.matrictime.network.request.LoginRequest;
import com.matrictime.network.request.UserRequest;
import com.matrictime.network.response.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/2/24 0024 13:43
 * @desc
 */
@Service
@Slf4j
public class UserDomainServiceImpl implements UserDomainService {

    @Autowired
    private NmplUserMapper userMapper;

    @Autowired
    private NmplLoginDetailMapper loginDetailMapper;

    /**
     * @title getUserByParamter
     * @param [loginRequest]
     * @return java.util.List<com.matrictime.network.dao.model.NmplUser>
     * @description
     * @author jiruyi
     * @create 2022/2/24 0024 13:43
     */
    @Override
    public List<NmplUser> getUserByParamter(LoginRequest loginRequest) {
        NmplUserExample userExample = new NmplUserExample();
        //用户名查询
        if (loginRequest.getType().equals(LoginTypeEnum.PASSWORD.getCode())){
            userExample.createCriteria().andLoginAccountEqualTo(loginRequest.getLoginAccount());
        }
        //手机号查询
        if (loginRequest.getType().equals(LoginTypeEnum.MOBILEPHONE.getCode())){
            userExample.createCriteria().andPhoneNumberEqualTo(loginRequest.getPhone());
        }
        List<NmplUser> userList =  userMapper.selectByExample(userExample);
        log.info("UserDomainService.getUserByParamter() result is :{}",userList);
        return userList;
    }

    /**
     * @title insertLoginDetail
     * @param [logintDetail]
     * @return int
     * @description
     * @author jiruyi
     * @create 2022/2/25 0025 9:25
     */
    @Override
    public int insertLoginDetail(NmplLoginDetail loginDetail) {
        if(ObjectUtils.isEmpty(loginDetail)){
            return NumberUtils.INTEGER_ZERO;
        }
        return loginDetailMapper.insertSelective(loginDetail);
    }

    /**
     * @title getUserById
     * @param [userId]
     * @return com.matrictime.network.dao.model.NmplUser
     * @description
     * @author jiruyi
     * @create 2022/2/28 0028 10:09
     */
    @Override
    public NmplUser getUserById(Long userId) {
        NmplUser user = userMapper.selectByPrimaryKey(userId);
        return user;
    }

    /**
     * @title getUserByPhone
     * @param [phone]
     * @return java.util.List<com.matrictime.network.dao.model.NmplUser>
     * @description
     * @author jiruyi
     * @create 2022/2/28 0028 11:25
     */
    @Override
    public  List<NmplUser> getUserByPhone(String phone) {
        NmplUserExample userExample = new NmplUserExample();
        userExample.createCriteria().andPhoneNumberEqualTo(phone).andIsExistEqualTo(true);
        return  userMapper.selectByExample(userExample);
    }

    /**
     * @title getUserByLoginAccount
     * @param [loginAccount]
     * @return java.util.List<com.matrictime.network.dao.model.NmplUser>
     * @description
     * @author jiruyi
     * @create 2022/2/28 0028 11:36
     */
    @Override
    public  List<NmplUser> getUserByLoginAccount(String loginAccount) {
        NmplUserExample userExample = new NmplUserExample();
        userExample.createCriteria().andLoginAccountEqualTo(loginAccount).andIsExistEqualTo(true);
        return  userMapper.selectByExample(userExample);
    }

    /**
     * @title insertUser
     * @param [userRequest]
     * @return int
     * @description
     * @author jiruyi
     * @create 2022/2/28 0028 11:36
     */
    @Override
    public int insertUser(UserRequest userRequest) {
        NmplUser record = new NmplUser();
        BeanUtils.copyProperties(userRequest,record);
        return  userMapper.insertSelective(record);
    }

    /**
     * @title updateUser
     * @param [userRequest]
     * @return int
     * @description
     * @author jiruyi
     * @create 2022/2/28 0028 15:47
     */
    @Override
    public int updateUser(UserRequest userRequest) {
        NmplUser record =NmplUser.builder().userId(Long.valueOf(userRequest.getUserId()))
                .loginAccount(userRequest.getLoginAccount())
                .nickName(userRequest.getNickName())
                .roleId(userRequest.getRoleId())
                .updateUser(userRequest.getUpdateUser())
                .phoneNumber(userRequest.getPhoneNumber()).build();
        return  userMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * @title deleteUser
     * @param [userRequest]
     * @return int
     * @description
     * @author jiruyi
     * @create 2022/2/28 0028 16:01
     */
    @Override
    public int deleteUser(UserRequest userRequest) {
        NmplUser record =NmplUser.builder().userId(Long.valueOf(userRequest.getUserId()))
                .isExist(false).build();
        return userMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * @title selectUserList
     * @param [userRequest]
     * @return java.util.List<com.matrictime.network.dao.model.NmplUser>
     * @description
     * @author jiruyi
     * @create 2022/2/28 0028 17:18
     */
    @Override
    public PageInfo<NmplUser> selectUserList(UserRequest userRequest) {
        NmplUserExample userExample = new NmplUserExample();
        NmplUserExample.Criteria criteria = userExample.createCriteria();
        if(!ObjectUtils.isEmpty(userRequest.getLoginAccount())){
            criteria.andLoginAccountEqualTo(userRequest.getLoginAccount());
        }
        if(!ObjectUtils.isEmpty(userRequest.getPhoneNumber())){
            criteria.andPhoneNumberEqualTo(userRequest.getPhoneNumber());
        }
        if(!ObjectUtils.isEmpty(userRequest.getCreateTime())){
            criteria.andCreateTimeEqualTo(new Date(Long.valueOf(userRequest.getCreateTime())));
        }
        if(!ObjectUtils.isEmpty(userRequest.getRemark())){
            criteria.andRoleIdEqualTo(userRequest.getRoleId());
        }
        //分页
        Page page = PageHelper.startPage(userRequest.getPageNo(),userRequest.getPageSize());
        List<NmplUser> list = userMapper.selectByExample(userExample);
        PageInfo<NmplUser> pageResult =  new PageInfo<>((int)page.getTotal(), page.getPages(), list);
        return  pageResult;
    }
}
