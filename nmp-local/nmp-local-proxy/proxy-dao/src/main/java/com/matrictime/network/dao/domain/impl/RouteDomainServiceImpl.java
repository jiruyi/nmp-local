package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.RouteDomainService;
import com.matrictime.network.dao.mapper.extend.RouteMapper;
import com.matrictime.network.modelVo.RouteVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class RouteDomainServiceImpl implements RouteDomainService {

    @Resource
    private RouteMapper routeMapper;

    @Override
    public int insertRoute(List<RouteVo> list) {
        return routeMapper.batchInsert(list);
    }
    @Override
    public int updateRoute(List<RouteVo> list) {
        return routeMapper.batchUpdate(list);
    }
}