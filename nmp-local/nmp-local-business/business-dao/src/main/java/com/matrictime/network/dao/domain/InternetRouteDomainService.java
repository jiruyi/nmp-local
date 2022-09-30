package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.InternetRouteVo;
import com.matrictime.network.request.InternetRouteRequest;
import com.matrictime.network.response.PageInfo;

/**
 * @author by wangqiang
 * @date 2022/9/29.
 */
public interface InternetRouteDomainService {
    int insert(InternetRouteRequest internetRouteRequest);

    int delete(InternetRouteRequest internetRouteRequest);

    int update(InternetRouteRequest internetRouteRequest);

    PageInfo<InternetRouteVo> select(InternetRouteRequest internetRouteRequest);
}
