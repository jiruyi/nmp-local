package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.InternetRouteRequest;
import com.matrictime.network.response.PageInfo;

/**
 * @author by wangqiang
 * @date 2022/9/29.
 */
public interface InternetRouteService {
    Result<Integer> insert(InternetRouteRequest internetRouteRequest);

    Result<Integer> delete(InternetRouteRequest internetRouteRequest);

    Result<Integer> update(InternetRouteRequest internetRouteRequest);

    Result<PageInfo> select(InternetRouteRequest internetRouteRequest);
}
