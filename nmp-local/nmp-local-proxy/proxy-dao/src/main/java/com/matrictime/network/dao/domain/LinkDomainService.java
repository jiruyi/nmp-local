package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.LinkVo;

import java.util.List;

public interface LinkDomainService {
    int insertLink(List<LinkVo> list);

    int updateLink(List<LinkVo> list);
}
