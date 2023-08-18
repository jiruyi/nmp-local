package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.request.LoginRequest;
import com.matrictime.network.request.UserInfo;
import com.matrictime.network.request.UserRequest;
import com.matrictime.network.response.PageInfo;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/2/24 0024 13:42
 * @desc
 */
public interface UserDomainService {

    /**
      * @title getUserByParamter
      * @param [loginRequest]
      * @return List<NmplUser>
      * @description
      * @author jiruyi
      * @create 2022/2/24 0024 13:43
      */
    List<NmplUser> getUserByParamter(LoginRequest loginRequest);

    /**
      * @title insertLoginDetail
      * @param [logintDetail]
      * @return int
      * @description
      * @author jiruyi
      * @create 2022/2/25 0025 9:24
      */
//    int insertLoginDetail(NmplLoginDetail loginDetail);


    /**
      * @title getUserById
      * @param [userId]
      * @return com.matrictime.network.dao.model.NmplUser
      * @description 
      * @author jiruyi
      * @create 2022/2/28 0028 10:08
      */
    NmplUser getUserById(Long userId);

    /**
      * @title getUserByPhone
      * @param [phone]
      * @return com.matrictime.network.dao.model.NmplUser
      * @description 
      * @author jiruyi
      * @create 2022/2/28 0028 11:19
      */
    List<NmplUser> getUserByPhone(String phone);

    /**
      * @title getUserByLoginAccount
      * @param [loginAccount]
      * @return com.matrictime.network.dao.model.NmplUser
      * @description 
      * @author jiruyi
      * @create 2022/2/28 0028 11:20
      */
    List<NmplUser> getUserByLoginAccount(String loginAccount);

    /**
      * @title insertUser
      * @param [userRequest]
      * @return int
      * @description 
      * @author jiruyi
      * @create 2022/2/28 0028 11:38
      */
    int insertUser(UserRequest userRequest);

    /**
      * @title updateUser
      * @param [userRequest]
      * @return int
      * @description 
      * @author jiruyi
      * @create 2022/2/28 0028 14:45
      */
    int updateUser(UserRequest userRequest);

    /**
      * @title deleteUser
      * @param [userRequest]
      * @return int
      * @description 
      * @author jiruyi
      * @create 2022/2/28 0028 15:56
      */
    int  deleteUser(UserRequest userRequest);

    /**
      * @title selectUserList
      * @param [userRequest]
      * @return java.util.List<com.matrictime.network.dao.model.NmplUser>
      * @description 查询用户列表
      * @author jiruyi
      * @create 2022/2/28 0028 17:17
      */
    PageInfo<NmplUser> selectUserList(UserRequest userRequest);
    
    /**
      * @title passwordReset
      * @param [userInfo]
      * @return int
      * @description 
      * @author jiruyi
      * @create 2022/3/2 0002 9:35
      */
    int passwordReset(UserInfo userInfo);

}
