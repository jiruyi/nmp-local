package com.matrictime.network.service;

import com.matrictime.network.api.request.UserFriendReq;
import com.matrictime.network.api.response.UserFriendResp;
import com.matrictime.network.model.Result;


public interface UserFriendsService {
    Result<UserFriendResp> selectUserFriend(UserFriendReq userFriendReq);
}
