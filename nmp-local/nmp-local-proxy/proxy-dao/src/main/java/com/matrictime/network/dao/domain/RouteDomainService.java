package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.RouteVo;

import java.util.List;


public interface RouteDomainService {
    int insertRoute(List<RouteVo> list);

    int updateRoute(List<RouteVo> list);
}
