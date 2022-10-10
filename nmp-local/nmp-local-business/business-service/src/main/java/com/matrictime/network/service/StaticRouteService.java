package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.StaticRouteRequest;
import com.matrictime.network.response.PageInfo;

/**
 * @author by wangqiang
 * @date 2022/10/9.
 */
public interface StaticRouteService {
    Result<Integer> insert(StaticRouteRequest staticRouteRequest);

    Result<Integer> delete(StaticRouteRequest staticRouteRequest);

    Result<Integer> update(StaticRouteRequest staticRouteRequest);

    Result<PageInfo> select(StaticRouteRequest staticRouteRequest);
}
