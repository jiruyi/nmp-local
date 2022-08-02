package com.matrictime.network.service;

import com.matrictime.network.api.request.GroupReq;
import com.matrictime.network.api.response.GroupResp;
import com.matrictime.network.model.Result;

public interface GroupService {
    Result<Integer> createGroup(GroupReq groupReq);

    Result<Integer> modifyGroup(GroupReq groupReq);

    Result<Integer> deleteGroup(GroupReq groupReq);

    Result<GroupResp> queryGroup(GroupReq groupReq);

}
