package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.dao.model.NmplLinkRelation;
import com.matrictime.network.dao.model.NmplLinkRelationExample;
import com.matrictime.network.modelVo.LinkRelationVo;
import com.matrictime.network.modelVo.RouteVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LinkRelationMapper {
    int batchInsert(@Param("list") List<LinkRelationVo> routeVos);

    int batchUpdate(@Param("list") List<LinkRelationVo> routeVos);
}