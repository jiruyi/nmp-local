package com.matrictime.network.service;

import com.matrictime.network.api.request.DeleteFriendReq;
import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.model.Result;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/31 0031 16:24
 * @desc
 */
public interface UserService {

    /**
      * @title modify
      * @param [userRequest]
      * @return com.matrictime.network.model.Result
      * @description
      * @author jiruyi
      * @create 2022/4/6 0006 8:54
      */
    Result modifyUserInfo(UserRequest userRequest);

    /**
      * @title deleteFriend
      * @param [deleteFriendReq]
      * @return com.matrictime.network.model.Result
      * @description 
      * @author jiruyi
      * @create 2022/4/12 0012 16:01
      */
    Result deleteFriend(DeleteFriendReq deleteFriendReq);


}
