package com.matrictime.network.service;

import com.matrictime.network.dao.model.extend.NmplDeviceInfoExt;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.RouteVo;
import com.matrictime.network.request.RouteRequest;
import com.matrictime.network.response.PageInfo;

import java.util.List;

public interface RouteService {
    Result<Integer> insertRoute(RouteRequest roteRequest);

    Result<Integer> deleteRoute(RouteRequest roteRequest);

    Result<Integer> updateRoute(RouteRequest roteRequest);

    Result<PageInfo<RouteVo>> selectRoute(RouteRequest routeRequest);

    List<NmplDeviceInfoExt> selectDevices();
}
