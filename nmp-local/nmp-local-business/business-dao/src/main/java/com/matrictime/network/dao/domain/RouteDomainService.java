package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.extend.NmplDeviceInfoExt;
import com.matrictime.network.modelVo.RouteVo;
import com.matrictime.network.request.RouteRequest;
import com.matrictime.network.response.PageInfo;

import java.util.List;


public interface RouteDomainService {
    int insertRoute(RouteRequest roteRequest);

    int deleteRoute(RouteRequest routeRequest);

    int updateRoute(RouteRequest routeRequest);

    PageInfo<RouteVo> selectRoute(RouteRequest routeRequest);

    List<NmplDeviceInfoExt> selectDevices();
}
