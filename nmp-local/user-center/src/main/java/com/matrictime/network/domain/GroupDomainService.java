package com.matrictime.network.domain;

import com.matrictime.network.api.modelVo.GroupVo;
import com.matrictime.network.api.request.GroupReq;
import com.matrictime.network.api.response.GroupResp;
import com.matrictime.network.dao.model.Group;

import java.util.List;

public interface GroupDomainService {
    public Integer createGroup(GroupReq groupReq);

    public Integer deleteGroup(GroupReq groupReq);

    public Integer modifyGroup(GroupReq groupReq);

    public GroupResp queryGroup(GroupReq groupReq);

}
