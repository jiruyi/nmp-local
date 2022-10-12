package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.RouteDomainService;
import com.matrictime.network.dao.mapper.extend.RouteMapper;
import com.matrictime.network.modelVo.NmplBusinessRouteVo;
import com.matrictime.network.modelVo.NmplInternetRouteVo;
import com.matrictime.network.modelVo.NmplStaticRouteVo;
import com.matrictime.network.modelVo.RouteVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class RouteDomainServiceImpl implements RouteDomainService {

    @Resource
    private RouteMapper routeMapper;

    @Override
    @Transactional
    public int insertRoute(List<RouteVo> list) {
        return routeMapper.batchInsert(list);
    }
    @Override
    @Transactional
    public int updateRoute(List<RouteVo> list) {
        return routeMapper.batchUpdate(list);
    }

    @Override
    @Transactional
    public int insertBusinessRoute(List<NmplBusinessRouteVo> list) {
        return routeMapper.batchBusinessInsert(list);
    }

    @Override
    @Transactional
    public int updateBusinessRoute(List<NmplBusinessRouteVo> list) {
        return routeMapper.batchBusinessUpdate(list);
    }

    @Override
    public int insertInternetRoute(List<NmplInternetRouteVo> list) {
        return routeMapper.batchInternetInsert(list);
    }

    @Override
    @Transactional
    public int updateInternetRoute(List<NmplInternetRouteVo> list) {
        return routeMapper.batchInternetUpdate(list);
    }

    @Override
    @Transactional
    public int insertStaticRoute(List<NmplStaticRouteVo> list) {
        return routeMapper.batchStaticInsert(list);
    }

    @Override
    @Transactional
    public int updateStaticRoute(List<NmplStaticRouteVo> list) {
        return routeMapper.batchStaticUpdate(list);
    }
}