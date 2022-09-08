package com.matrictime.network.dao.domain;

import com.matrictime.network.api.request.AppCodeRequest;
import com.matrictime.network.api.request.DeleteFriendReq;
import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.dao.model.User;

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

    int updateAppCode(AppCodeRequest appCodeRequest);

    User selectByCondition(UserRequest userRequest);

    User queryUserByqueryParam(UserRequest userRequest);

}
