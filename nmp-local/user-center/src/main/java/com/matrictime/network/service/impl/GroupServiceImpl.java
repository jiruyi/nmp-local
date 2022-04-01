package com.matrictime.network.service.impl;

import com.matrictime.network.api.request.GroupReq;
import com.matrictime.network.api.response.GroupResp;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.domain.GroupDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;

public class GroupServiceImpl extends SystemBaseService implements GroupService {
    @Autowired
    GroupDomainService groupDomainService;


    @Override
    public Result<Integer> createGroup(GroupReq groupReq) {
        return null;
    }

    @Override
    public Result<Integer> modifyGroup(GroupReq groupReq) {
        return null;
    }

    @Override
    public Result<Integer> deleteGroup(GroupReq groupReq) {
        return null;
    }

    @Override
    public Result<GroupResp> queryGroup(GroupReq groupReq) {
        return null;
    }
}
