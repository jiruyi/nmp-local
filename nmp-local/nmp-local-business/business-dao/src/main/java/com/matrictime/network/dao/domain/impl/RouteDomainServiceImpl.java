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

    @Override
    public int insertRoute(RouteRequest routeRequest) {
        List<RouteVo> list = nmplRouteMapper.query(routeRequest);
        if(list.size() > 0){
            return 2;
        }
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
        PageInfo<RouteVo> pageResult =  new PageInfo<>();
        List<RouteVo> routeVoList;
        //网管前端查询数据进行分页
        if(routeRequest.getConditionType() == 1){
            Page page = PageHelper.startPage(routeRequest.getPageNo(),routeRequest.getPageSize());
            routeVoList = nmplRouteMapper.selectRoute(routeRequest);
            pageResult.setCount((int) page.getTotal());
            pageResult.setPages(page.getPages());
        }else {
            routeVoList = nmplRouteMapper.selectRoute(routeRequest);
        }
        pageResult.setList(routeVoList);
        return  pageResult;
    }

    @Override
    public List<NmplDeviceInfoExt> selectDevices() {
        return nmplRouteMapper.selectDevices();
    }
}