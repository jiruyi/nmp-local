package com.matrictime.network.domain;

import com.matrictime.network.api.request.GroupReq;
import com.matrictime.network.api.request.UserGroupReq;
import com.matrictime.network.api.response.UserGroupResp;
import com.matrictime.network.dao.model.Group;
import com.matrictime.network.dao.model.UserGroup;

import java.util.List;

public interface UserGroupDomianService {
    public Integer createGroup(UserGroupReq userGroupReq);

    public Integer deleteGroup(UserGroupReq userGroupReq);

    public Integer modifyGroup(UserGroupReq userGroupReq);

    public List<UserGroupResp> queryGroup(UserGroupReq userGroupReq);

}
