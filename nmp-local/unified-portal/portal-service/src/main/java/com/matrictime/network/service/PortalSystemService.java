package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.PortalSystemVo;
import com.matrictime.network.request.*;
import com.matrictime.network.response.PageInfo;

import java.util.List;

public interface PortalSystemService {

    Result addSystem (AddSystemReq req);

    Result delSystem(DelSystemReq req);

    Result updSystem(UpdSystemReq req);

    Result<PageInfo> querySystemByPage(QuerySystemByPageReq req);

    Result<List<PortalSystemVo>> querySystem(QuerySystemReq req);
}
