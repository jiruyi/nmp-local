package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.dao.domain.StaticRouteDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplStaticRouteMapper;
import com.matrictime.network.dao.model.NmplBaseStationInfo;
import com.matrictime.network.dao.model.NmplBaseStationInfoExample;
import com.matrictime.network.dao.model.NmplStaticRoute;
import com.matrictime.network.dao.model.NmplStaticRouteExample;
import com.matrictime.network.modelVo.StaticRouteVo;
import com.matrictime.network.request.StaticRouteRequest;
import com.matrictime.network.response.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.matrictime.network.constant.DataConstants.IS_EXIST;

/**
 * @author by wangqiang
 * @date 2022/10/9.
 */
@Service
public class StaticRouteDomainServiceImpl implements StaticRouteDomainService {

    @Resource
    private NmplStaticRouteMapper nmplStaticRouteMapper;

    @Resource
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

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
        NmplStaticRouteExample.Criteria criteria1 = nmplStaticRouteExample.createCriteria();
        nmplStaticRouteExample.setOrderByClause("update_time desc");
        if(!StringUtils.isEmpty(staticRouteRequest.getNetworkId())){
            criteria.andNetworkIdLike("%"+staticRouteRequest.getNetworkId()+"%");
            criteria1.andNetworkIdLike("%"+staticRouteRequest.getNetworkId()+"%");
        }
        if(!StringUtils.isEmpty(staticRouteRequest.getStationId())){
            criteria.andStationIdEqualTo(staticRouteRequest.getStationId());
            criteria1.andStationIdEqualTo(staticRouteRequest.getStationId());
        }
        if(!StringUtils.isEmpty(staticRouteRequest.getServerIp())){
            criteria.andServerIpLike("%"+staticRouteRequest.getServerIp()+"%");
            criteria1.andIpV6Like("%"+staticRouteRequest.getServerIp()+"%");
        }
        criteria.andIsExistEqualTo(IS_EXIST);
        criteria1.andIsExistEqualTo(IS_EXIST);
        nmplStaticRouteExample.or(criteria1);
        //分页查询数据
        Page page = PageHelper.startPage(staticRouteRequest.getPageNo(),staticRouteRequest.getPageSize());
        List<NmplStaticRoute> nmplInternetRoutes = nmplStaticRouteMapper.selectByExample(nmplStaticRouteExample);
        PageInfo<StaticRouteVo> pageInfo = new PageInfo<>();


//        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
//        nmplBaseStationInfoExample.createCriteria().andIsExistEqualTo(true);
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(null);
        Map<String,String> baseStationInfoMap = new HashMap<>();
        for (int i = 0; i < nmplBaseStationInfos.size(); i++) {
            baseStationInfoMap.put(nmplBaseStationInfos.get(i).getStationId(),nmplBaseStationInfos.get(i).getStationNetworkId());
        }
        for(NmplStaticRoute nmplStaticRoute: nmplInternetRoutes){
            StaticRouteVo staticRouteVo = new StaticRouteVo();
            BeanUtils.copyProperties(nmplStaticRoute,staticRouteVo);
            staticRouteVo.setRouteNetworkId(baseStationInfoMap.get(staticRouteVo.getStationId())+"-"+staticRouteVo.getNetworkId());
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
