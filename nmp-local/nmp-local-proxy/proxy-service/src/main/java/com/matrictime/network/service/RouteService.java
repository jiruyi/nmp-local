package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.request.AddRouteRequest;
import com.matrictime.network.request.UpdateRouteRequest;

import java.util.List;

public interface RouteService {

    Result<Integer> addRoute(List<RouteVo> voList);

    Result<Integer> updateRoute(RouteVo vo);

    Result<Integer> addBusinessRoute(NmplBusinessRouteVo vo);

    Result<Integer> updateBusinessRoute(NmplBusinessRouteVo vo);

    Result<Integer> addInternetRoute(NmplInternetRouteVo vo);

    Result<Integer> updateInternetRoute(NmplInternetRouteVo vo);

    Result<Integer> addStaticRoute(NmplStaticRouteVo vo);

    Result<Integer> updateStaticRoute(NmplStaticRouteVo vo);

    void initInfo(List<CenterRouteVo> centerRouteVos);

    void businessRouteInitInfo(List<NmplBusinessRouteVo> nmplBusinessRouteVo);

    void InternetRouteInitInfo(List<NmplInternetRouteVo> nmplInternetRouteVo);

    void StaticRouteInitInfo(List<NmplStaticRouteVo> nmplStaticRouteVo);
}
