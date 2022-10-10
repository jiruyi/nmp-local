package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.StaticRouteVo;
import com.matrictime.network.request.StaticRouteRequest;
import com.matrictime.network.response.PageInfo;

/**
 * @author by wangqiang
 * @date 2022/10/9.
 */
public interface StaticRouteDomainService {
    int insert(StaticRouteRequest staticRouteRequest);

    int delete(StaticRouteRequest staticRouteRequest);

    int update(StaticRouteRequest staticRouteRequest);

    PageInfo<StaticRouteVo> select(StaticRouteRequest staticRouteRequest);
}
