package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.*;

import java.util.List;

public interface LinkRelationService {

    Result<Integer> updateLink(List<ProxyLinkVo> list);

    void initInfo(List<ProxyLinkVo> localLinkVos);
}
