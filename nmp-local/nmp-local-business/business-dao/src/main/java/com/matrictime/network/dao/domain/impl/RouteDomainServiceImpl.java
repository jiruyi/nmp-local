package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.dao.domain.RouteDomainService;
import com.matrictime.network.dao.mapper.NmplRouteMapper;
import com.matrictime.network.dao.mapper.extend.NmplSignalExtMapper;
import com.matrictime.network.dao.model.extend.NmplDeviceInfoExt;
import com.matrictime.network.modelVo.LinkRelationVo;
import com.matrictime.network.modelVo.RouteVo;
import com.matrictime.network.request.RouteRequest;
import com.matrictime.network.response.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class RouteDomainServiceImpl implements RouteDomainService {

    @Resource
    private NmplRouteMapper nmplRouteMapper;

    @Resource
    NmplSignalExtMapper nmplSignalExtMapper;

    @Override
    public int insertRoute(RouteRequest routeRequest) {
        return nmplRouteMapper.insertRoute(routeRequest);
    }

    @Override
    public int deleteRoute(RouteRequest routeRequest) {
        return nmplRouteMapper.deleteRoute(routeRequest);
    }

    @Override
    public int updateRoute(RouteRequest routeRequest) {
        return nmplRouteMapper.updateRoute(routeRequest);
    }

    @Override
    public PageInfo<RouteVo> selectRoute(RouteRequest routeRequest) {
        Page page = PageHelper.startPage(routeRequest.getPageNo(),routeRequest.getPageSize());
        List<RouteVo> routeVoList = nmplRouteMapper.selectRoute(routeRequest);
        PageInfo<RouteVo> pageResult =  new PageInfo<>();
        pageResult.setList(routeVoList);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return  pageResult;
    }

    @Override
    public List<NmplDeviceInfoExt> selectDevices() {
        return nmplSignalExtMapper.selectDevices();
    }
}