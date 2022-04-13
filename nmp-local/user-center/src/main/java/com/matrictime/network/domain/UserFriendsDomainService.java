package com.matrictime.network.domain;

import com.matrictime.network.api.request.UserRequest;

public interface UserFriendsDomainService {

    //用户注销
    int cancelUser(UserRequest userRequest);
}
