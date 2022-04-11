package com.matrictime.network.service;

import com.matrictime.network.api.request.GroupReq;
import com.matrictime.network.api.request.UserGroupReq;
import com.matrictime.network.api.response.GroupResp;
import com.matrictime.network.api.response.UserGroupResp;
import com.matrictime.network.model.Result;

public interface UserGroupService {
    Result<Integer> createUserGroup(UserGroupReq userGroupReq);

    Result<Integer> modifyUserGroup(UserGroupReq userGroupReq);

    Result<Integer> deleteUserGroup(UserGroupReq userGroupReq);

    Result<UserGroupResp> queryUserGroup(UserGroupReq userGroupReq);
}
