package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.LoginRequest;
import com.matrictime.network.request.UserInfo;
import com.matrictime.network.request.UserRequest;
import com.matrictime.network.response.LoginResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.response.UserInfoResp;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/2/24 0024 10:22
 * @desc
 */
public interface UserService {

    /**
      * @title login 登录接口
      * @param [loginRequest]
      * @return com.matrictime.network.model.Result<com.matrictime.network.response.LoginResponse>
      * @description 
      * @author jiruyi
      * @create 2022/2/24 0024 11:33
      */
    Result<LoginResponse> login(LoginRequest loginRequest);

    /**
      * @title insertUser
      * @param [userRequest]
      * @return com.matrictime.network.model.Result<java.lang.Integer>
      * @description 
      * @author jiruyi
      * @create 2022/2/28 0028 11:36
      */
    Result<Integer> insertUser(UserRequest userRequest);



    /**
      * @title updateUser
      * @param [userRequest]
      * @return com.matrictime.network.model.Result<java.lang.Integer>
      * @description  更新用户
      * @author jiruyi
      * @create 2022/2/28 0028 14:41
      */
    Result<Integer> updateUser(UserRequest userRequest);

    /**
      * @title deleteUser
      * @param [userRequest]
      * @return com.matrictime.network.model.Result<java.lang.Integer>
      * @description 
      * @author jiruyi
      * @create 2022/2/28 0028 15:53
      */
    Result<Integer> deleteUser(UserRequest userRequest);

    /**
      * @title selectUserList
      * @param [userRequest]
      * @return com.matrictime.network.model.Result<java.lang.Integer>
      * @description 
      * @author jiruyi
      * @create 2022/2/28 0028 16:46
      */
    Result<PageInfo> selectUserList(UserRequest userRequest);


    /**
      * @title passwordReset
      * @param [userInfo]
      * @return com.matrictime.network.model.Result<java.lang.Integer>
      * @description  密码重置
      * @author jiruyi
      * @create 2022/3/1 0001 17:05
      */
    Result<Integer> passwordReset(UserInfo userInfo);

    Result<UserInfoResp> getUserInfo(UserRequest userRequest);
    
}
