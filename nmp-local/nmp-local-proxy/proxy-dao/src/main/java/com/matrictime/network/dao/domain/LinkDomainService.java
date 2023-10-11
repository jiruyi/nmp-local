package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.ProxyLinkVo;

import java.util.List;

public interface LinkDomainService {
    int insertLink(List<ProxyLinkVo> list);

    int updateLink(List<ProxyLinkVo> list);
}
