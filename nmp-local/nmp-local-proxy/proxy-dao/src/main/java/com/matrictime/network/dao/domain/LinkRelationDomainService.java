package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.LinkRelationVo;
import java.util.List;

public interface LinkRelationDomainService {
    int insertLinkRelation(List<LinkRelationVo> list);

    int updateLinkRelation(List<LinkRelationVo> list);
}
