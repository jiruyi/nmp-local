package com.matrictime.network.domain;

import com.matrictime.network.api.request.DeleteFriendReq;
import com.matrictime.network.api.request.UserRequest;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/31 0031 16:23
 * @desc
 */
public interface UserDomainService {

    int modifyUserInfo(UserRequest userRequest);

    int deleteFriend(DeleteFriendReq deleteFriendReq);
}
