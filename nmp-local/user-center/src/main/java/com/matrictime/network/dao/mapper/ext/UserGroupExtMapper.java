package com.matrictime.network.dao.mapper.ext;

import com.matrictime.network.api.request.UserGroupReq;
import com.matrictime.network.api.response.UserGroupResp;
import com.matrictime.network.dao.model.UserGroup;

import java.util.List;

public interface UserGroupExtMapper {
    Integer updateByUserIdAndGroupId(UserGroupReq userGroupReq);

    List<UserGroupResp> selectByCondition(UserGroupReq userGroupReq);
}
