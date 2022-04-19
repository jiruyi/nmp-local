package com.matrictime.network.dao.mapper.ext;

import com.matrictime.network.api.modelVo.UserFriendVo;
import com.matrictime.network.api.modelVo.UserVo;
import com.matrictime.network.api.request.UserFriendReq;
import com.matrictime.network.api.request.UserRequest;

import java.util.List;

public interface UserFriendExtMapper {
    List<UserFriendVo> selectUserFriend(UserFriendReq userFriendReq);

    int insertFriend(UserFriendReq userFriendReq);

    UserVo selectUserInfo(UserRequest userRequest);
}
