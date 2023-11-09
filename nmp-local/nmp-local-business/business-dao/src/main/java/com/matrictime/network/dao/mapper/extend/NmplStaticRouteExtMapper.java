package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.StaticRouteVo;
import com.matrictime.network.request.StaticRouteRequest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/11/9.
 */
public interface NmplStaticRouteExtMapper {

    List<StaticRouteVo> selectStaticRoute(StaticRouteRequest staticRouteRequest);
}
