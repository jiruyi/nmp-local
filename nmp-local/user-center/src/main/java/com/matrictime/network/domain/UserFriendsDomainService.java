package com.matrictime.network.domain;


import com.matrictime.network.api.modelVo.AddUserRequestVo;
import com.matrictime.network.api.modelVo.GroupVo;
import com.matrictime.network.api.modelVo.UserFriendVo;
import com.matrictime.network.api.modelVo.UserVo;
import com.matrictime.network.api.request.AddUserRequestReq;
import com.matrictime.network.api.request.GroupReq;
import com.matrictime.network.api.request.UserFriendReq;
import com.matrictime.network.api.request.UserRequest;

import java.util.List;

public interface UserFriendsDomainService {
    List<UserFriendVo> selectUserFriend(UserFriendReq userFriendReq);

    int addFriends(AddUserRequestReq addUserRequestReq);

    int insertFriend(UserFriendReq userFriendReq);

    UserVo selectUserInfo(UserRequest userRequest);


    List<AddUserRequestVo> getAddUserInfo(AddUserRequestReq addUserRequestReq);

    GroupVo selectGroupInfo(GroupReq groupReq);

    int update(AddUserRequestReq addUserRequestReq);
}
