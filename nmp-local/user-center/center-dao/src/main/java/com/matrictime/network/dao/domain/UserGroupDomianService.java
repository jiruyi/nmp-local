package com.matrictime.network.dao.domain;

import com.matrictime.network.api.modelVo.UserGroupVo;
import com.matrictime.network.api.request.UserGroupReq;

import java.util.List;
import java.util.Map;

public interface UserGroupDomianService {
    public Integer createUserGroup(UserGroupReq userGroupReq);

    public Integer deleteUserGroup(UserGroupReq userGroupReq);

    public Integer modifyUserGroup(UserGroupReq userGroupReq);

    public List<UserGroupVo> queryUserGroup(UserGroupReq userGroupReq);


    public Map<String,List<UserGroupVo>> queryUserGroupByGroupIds(List<String>groupIds,String owner);

}
