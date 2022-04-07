package com.matrictime.network.domain;

import com.matrictime.network.api.modelVo.UserGroupVo;
import com.matrictime.network.api.request.GroupReq;
import com.matrictime.network.api.request.UserGroupReq;
import com.matrictime.network.api.response.UserGroupResp;
import com.matrictime.network.dao.model.Group;
import com.matrictime.network.dao.model.UserGroup;

import java.util.List;

public interface UserGroupDomianService {
    public Integer createUserGroup(UserGroupReq userGroupReq);

    public Integer deleteUserGroup(UserGroupReq userGroupReq);

    public Integer modifyUserGroup(UserGroupReq userGroupReq);

    public List<UserGroupVo> queryUserGroup(UserGroupReq userGroupReq);

}
