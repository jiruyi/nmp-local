package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.NmplBusinessRouteVo;
import com.matrictime.network.modelVo.NmplInternetRouteVo;
import com.matrictime.network.modelVo.NmplStaticRouteVo;
import com.matrictime.network.modelVo.RouteVo;

import java.util.List;


public interface RouteDomainService {
    int insertRoute(List<RouteVo> list);

    int updateRoute(List<RouteVo> list);

    int insertBusinessRoute(List<NmplBusinessRouteVo> list);

    int updateBusinessRoute(List<NmplBusinessRouteVo> list);

    int insertInternetRoute(List<NmplInternetRouteVo> list);

    int updateInternetRoute(List<NmplInternetRouteVo> list);

    int insertStaticRoute(List<NmplStaticRouteVo> list);

    int updateStaticRoute(List<NmplStaticRouteVo> list);
}
