package com.matrictime.network.domain;


import com.matrictime.network.api.modelVo.UserFriendVo;
import com.matrictime.network.api.request.AddUserRequestReq;
import com.matrictime.network.api.request.UserFriendReq;

import java.util.List;

public interface UserFriendsDomainService {
    List<UserFriendVo> selectUserFriend(UserFriendReq userFriendReq);

    int addFriends(AddUserRequestReq addUserRequestReq);
}
