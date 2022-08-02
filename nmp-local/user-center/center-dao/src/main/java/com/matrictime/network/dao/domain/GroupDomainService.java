package com.matrictime.network.dao.domain;

import com.matrictime.network.api.modelVo.GroupVo;
import com.matrictime.network.api.request.GroupReq;

import java.util.List;

public interface GroupDomainService {
    public Integer createGroup(GroupReq groupReq);

    public Integer deleteGroup(GroupReq groupReq);

    public Integer modifyGroup(GroupReq groupReq);

    public List<GroupVo> queryGroup(GroupReq groupReq);

}
