package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.dao.domain.StaticRouteDomainService;
import com.matrictime.network.dao.mapper.NmplStaticRouteMapper;
import com.matrictime.network.dao.model.NmplStaticRoute;
import com.matrictime.network.dao.model.NmplStaticRouteExample;
import com.matrictime.network.modelVo.StaticRouteVo;
import com.matrictime.network.request.StaticRouteRequest;
import com.matrictime.network.response.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2022/10/9.
 */
@Service
public class StaticRouteDomainServiceImpl implements StaticRouteDomainService {

    @Resource
    private NmplStaticRouteMapper nmplStaticRouteMapper;

    @Override
    public int insert(StaticRouteRequest staticRouteRequest) {
        NmplStaticRoute nmplStaticRoute = new NmplStaticRoute();
        BeanUtils.copyProperties(staticRouteRequest,nmplStaticRoute);
        return nmplStaticRouteMapper.insertSelective(nmplStaticRoute);
    }

    @Override
    public int delete(StaticRouteRequest staticRouteRequest) {
        NmplStaticRouteExample nmplInternetRouteExample = constructUpdateCondition(staticRouteRequest);
        NmplStaticRoute nmplStaticRoute = constructUpdateDate(staticRouteRequest);
        nmplStaticRoute.setIsExist(false);
        return nmplStaticRouteMapper.updateByExampleSelective(nmplStaticRoute,nmplInternetRouteExample);
    }

    @Override
    public int update(StaticRouteRequest staticRouteRequest) {
        NmplStaticRouteExample nmplStaticRouteExample = constructUpdateCondition(staticRouteRequest);
        NmplStaticRoute nmplStaticRoute = constructUpdateDate(staticRouteRequest);
        return nmplStaticRouteMapper.updateByExampleSelective(nmplStaticRoute,nmplStaticRouteExample);
    }

    @Override
    public PageInfo<StaticRouteVo> select(StaticRouteRequest staticRouteRequest) {
        List<StaticRouteVo> list = new ArrayList<>();
        NmplStaticRouteExample nmplStaticRouteExample = new NmplStaticRouteExample();
        NmplStaticRouteExample.Criteria criteria = nmplStaticRouteExample.createCriteria();
        if(!StringUtils.isEmpty(staticRouteRequest.getNetworkId())){
            criteria.andNetworkIdEqualTo(staticRouteRequest.getNetworkId());
        }
        if(!StringUtils.isEmpty(staticRouteRequest.getServerIp())){
            criteria.andServerIpEqualTo(staticRouteRequest.getServerIp());
        }
        //分页查询数据
        Page page = PageHelper.startPage(staticRouteRequest.getPageNo(),staticRouteRequest.getPageSize());
        List<NmplStaticRoute> nmplInternetRoutes = nmplStaticRouteMapper.selectByExample(nmplStaticRouteExample);
        PageInfo<StaticRouteVo> pageInfo = new PageInfo<>();
        for(NmplStaticRoute nmplStaticRoute: nmplInternetRoutes){
            StaticRouteVo staticRouteVo = new StaticRouteVo();
            BeanUtils.copyProperties(nmplStaticRoute,staticRouteVo);
            list.add(staticRouteVo);
        }
        pageInfo.setList(list);
        pageInfo.setCount((int) page.getTotal());
        pageInfo.setPages(page.getPages());
        return pageInfo;
    }



    /**
     * 构造跟新数据
     * @param staticRouteRequest
     * @return
     */
    private NmplStaticRoute constructUpdateDate(StaticRouteRequest staticRouteRequest){
        NmplStaticRoute nmplStaticRoute = new NmplStaticRoute();
        BeanUtils.copyProperties(staticRouteRequest,nmplStaticRoute);
        return nmplStaticRoute;
    }

    /**
     * 构造更新条件
     * @param staticRouteRequest
     * @return
     */
    private NmplStaticRouteExample constructUpdateCondition(StaticRouteRequest staticRouteRequest){
        NmplStaticRouteExample nmplStaticRouteExample = new NmplStaticRouteExample();
        NmplStaticRouteExample.Criteria criteria = nmplStaticRouteExample.createCriteria();
        criteria.andIdEqualTo(staticRouteRequest.getId());
        return nmplStaticRouteExample;
    }
}
