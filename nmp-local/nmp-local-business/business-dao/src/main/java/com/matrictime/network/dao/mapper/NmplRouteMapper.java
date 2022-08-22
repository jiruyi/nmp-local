package com.matrictime.network.dao.mapper;

import com.matrictime.network.dao.model.extend.NmplDeviceInfoExt;
import com.matrictime.network.modelVo.RouteVo;
import com.matrictime.network.request.RouteRequest;

import java.util.List;

public interface NmplRouteMapper {
    int insertRoute(RouteRequest routeRequest);

    int deleteRoute(RouteRequest routeRequest);

    int updateRoute(RouteRequest routeRequest);

    List<RouteVo> selectRoute(RouteRequest routeRequest);

    List<RouteVo> query(RouteRequest routeRequest);

    List<RouteVo> selectById(RouteRequest routeRequest);

    List<NmplDeviceInfoExt> selectDevices();
}
