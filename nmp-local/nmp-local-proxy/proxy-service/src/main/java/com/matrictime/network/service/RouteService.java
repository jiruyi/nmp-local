package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.RouteVo;
import com.matrictime.network.request.AddRouteRequest;
import com.matrictime.network.request.UpdateRouteRequest;

import java.util.List;

public interface RouteService {

    Result<Integer> addRoute(List<RouteVo> voList);

    Result<Integer> updateRoute(RouteVo req);
}
