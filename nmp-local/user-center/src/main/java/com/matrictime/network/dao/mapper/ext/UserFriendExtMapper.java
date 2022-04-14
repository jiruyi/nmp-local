package com.matrictime.network.dao.mapper.ext;

import com.matrictime.network.api.modelVo.UserFriendVo;
import com.matrictime.network.api.request.UserFriendReq;

import java.util.List;

public interface UserFriendExtMapper {
    List<UserFriendVo> selectUserFriend(UserFriendReq userFriendReq);

    int addFriends(UserFriendReq userFriendReq);
}
