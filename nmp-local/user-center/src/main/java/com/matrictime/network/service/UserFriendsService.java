package com.matrictime.network.service;

import com.matrictime.network.api.request.AddUserRequestReq;
import com.matrictime.network.api.request.UserFriendReq;
import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.api.response.AddUserRequestResp;
import com.matrictime.network.api.response.UserFriendResp;
import com.matrictime.network.model.Result;


public interface UserFriendsService {
    Result<UserFriendResp> selectUserFriend(UserFriendReq userFriendReq);

    Result<Integer> addFriends(AddUserRequestReq addUserRequestReq);

    Result<AddUserRequestResp> getAddUserInfo(AddUserRequestReq addUserRequestReq);

    Result modifyUserInfo(UserRequest userRequest);

}
