package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.NmplBusinessRouteVo;
import com.matrictime.network.modelVo.NmplInternetRouteVo;
import com.matrictime.network.modelVo.NmplStaticRouteVo;
import com.matrictime.network.modelVo.RouteVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RouteMapper {
    int batchInsert(@Param("list") List<RouteVo> routeVos);

    int batchUpdate(@Param("list") List<RouteVo> routeVos);

    int batchBusinessInsert(@Param("list") List<NmplBusinessRouteVo> routeVos);

    int batchBusinessUpdate(@Param("list") List<NmplBusinessRouteVo> routeVos);

    int batchInternetInsert(@Param("list") List<NmplInternetRouteVo> routeVos);

    int batchInternetUpdate(@Param("list") List<NmplInternetRouteVo> routeVos);

    int batchStaticInsert(@Param("list") List<NmplStaticRouteVo> routeVos);

    int batchStaticUpdate(@Param("list") List<NmplStaticRouteVo> routeVos);
}