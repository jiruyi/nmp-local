package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.RouteDomainService;
import com.matrictime.network.dao.mapper.extend.RouteMapper;
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
}