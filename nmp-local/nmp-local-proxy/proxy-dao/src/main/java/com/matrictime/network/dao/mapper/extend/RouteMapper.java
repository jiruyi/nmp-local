package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.RouteVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RouteMapper {
    int batchInsert(@Param("list") List<RouteVo> routeVos);

    int batchUpdate(@Param("list") List<RouteVo> routeVos);
}