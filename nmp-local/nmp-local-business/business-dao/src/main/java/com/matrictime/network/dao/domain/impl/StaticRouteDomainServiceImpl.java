package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.util.DecimalConversionUtil;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.dao.domain.StaticRouteDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplStaticRouteMapper;
import com.matrictime.network.dao.mapper.extend.NmplStaticRouteExtMapper;
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
import org.springframework.util.CollectionUtils;
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

    @Resource
    private NmplStaticRouteExtMapper staticRouteExtMapper;

    @Override
    public int insert(StaticRouteRequest staticRouteRequest) {

        //判断入网id
        NmplStaticRouteExample staticRouteExample = new NmplStaticRouteExample();
        NmplStaticRouteExample.Criteria criteria = staticRouteExample.createCriteria();
        criteria.andNetworkIdEqualTo(staticRouteRequest.getNetworkId());
        criteria.andIsExistEqualTo(true);
        List<NmplStaticRoute> staticRoutes = nmplStaticRouteMapper.selectByExample(staticRouteExample);
        if(!CollectionUtils.isEmpty(staticRoutes)) {
            throw new RuntimeException("入网Id重复");
        }

        //判断设备Id
        NmplStaticRouteExample example = new NmplStaticRouteExample();
        NmplStaticRouteExample.Criteria staticRouteExampleCriteria = example.createCriteria();
        staticRouteExampleCriteria.andDeviceIdEqualTo(staticRouteRequest.getDeviceId());
        staticRouteExampleCriteria.andIsExistEqualTo(true);
        List<NmplStaticRoute> nmplStaticRoutes = nmplStaticRouteMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(nmplStaticRoutes)) {
            throw new RuntimeException("设备Id重复");
        }
        NmplStaticRoute nmplStaticRoute = new NmplStaticRoute();
        BeanUtils.copyProperties(staticRouteRequest,nmplStaticRoute);
        return nmplStaticRouteMapper.insertSelective(nmplStaticRoute);
    }

    @Override
    public int delete(StaticRouteRequest staticRouteRequest) {
        NmplStaticRouteExample nmplInternetRouteExample = constructUpdateCondition(staticRouteRequest);
        NmplStaticRoute nmplStaticRoute = constructUpdateData(staticRouteRequest);
        nmplStaticRoute.setIsExist(false);
        return nmplStaticRouteMapper.updateByExampleSelective(nmplStaticRoute,nmplInternetRouteExample);
    }

    @Override
    public int update(StaticRouteRequest staticRouteRequest) {

        //判断入网id
        NmplStaticRouteExample staticRouteExample = new NmplStaticRouteExample();
        NmplStaticRouteExample.Criteria criteria = staticRouteExample.createCriteria();
        criteria.andNetworkIdEqualTo(staticRouteRequest.getNetworkId());
        criteria.andIsExistEqualTo(true);
        criteria.andIdNotEqualTo(staticRouteRequest.getId());
        List<NmplStaticRoute> staticRoutes = nmplStaticRouteMapper.selectByExample(staticRouteExample);
        if(!CollectionUtils.isEmpty(staticRoutes)) {
            throw new RuntimeException("入网Id重复");
        }

        //判断设备id
        NmplStaticRouteExample example = new NmplStaticRouteExample();
        NmplStaticRouteExample.Criteria staticRouteExampleCriteria = example.createCriteria();
        staticRouteExampleCriteria.andDeviceIdEqualTo(staticRouteRequest.getDeviceId());
        staticRouteExampleCriteria.andIdNotEqualTo(staticRouteRequest.getId());
        staticRouteExampleCriteria.andIsExistEqualTo(true);
        List<NmplStaticRoute> nmplStaticRoutes = nmplStaticRouteMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(nmplStaticRoutes)) {
            throw new RuntimeException("设备Id重复");
        }

        NmplStaticRouteExample nmplStaticRouteExample = constructUpdateCondition(staticRouteRequest);
        NmplStaticRoute nmplStaticRoute = constructUpdateData(staticRouteRequest);
        return nmplStaticRouteMapper.updateByExampleSelective(nmplStaticRoute,nmplStaticRouteExample);
    }

    @Override
    public PageInfo<StaticRouteVo> select(StaticRouteRequest staticRouteRequest) {
        //分页查询数据
        Page page = PageHelper.startPage(staticRouteRequest.getPageNo(),staticRouteRequest.getPageSize());
        List<StaticRouteVo> staticRouteVos = staticRouteExtMapper.selectStaticRoute(staticRouteRequest);
        PageInfo<StaticRouteVo> pageInfo = new PageInfo<>();
//        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
//        nmplBaseStationInfoExample.createCriteria().andIsExistEqualTo(true);
//        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(null);
//        Map<String,String> baseStationInfoMap = new HashMap<>();
//        for (int i = 0; i < nmplBaseStationInfos.size(); i++) {
//            baseStationInfoMap.put(nmplBaseStationInfos.get(i).getStationId(),nmplBaseStationInfos.get(i).getStationNetworkId());
//        }
//        for(NmplStaticRoute nmplStaticRoute: nmplInternetRoutes){
//            StaticRouteVo staticRouteVo = new StaticRouteVo();
//            BeanUtils.copyProperties(nmplStaticRoute,staticRouteVo);
//            staticRouteVo.setRouteNetworkId(baseStationInfoMap.get(staticRouteVo.getStationId())+"-"+staticRouteVo.getNetworkId());
//            list.add(staticRouteVo);
//        }
        pageInfo.setList(staticRouteVos);
        pageInfo.setCount((int) page.getTotal());
        pageInfo.setPages(page.getPages());
        return pageInfo;
    }



    /**
     * 构造跟新数据
     * @param staticRouteRequest
     * @return
     */
    private NmplStaticRoute constructUpdateData(StaticRouteRequest staticRouteRequest){
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

    /**
     * 获取所有ID
     * @return
     */
    private List<String> selectID(StaticRouteRequest staticRouteRequest){
        NmplStaticRouteExample nmplStaticRouteExample = new NmplStaticRouteExample();
        NmplStaticRouteExample.Criteria criteria = nmplStaticRouteExample.createCriteria();
        criteria.andIsExistEqualTo(true);
        List<NmplStaticRoute> nmplStaticRoutes = nmplStaticRouteMapper.selectByExample(nmplStaticRouteExample);
        if(CollectionUtils.isEmpty(nmplStaticRoutes)){
            return null;
        }
        if(staticRouteRequest.getId() != null){
            for(NmplStaticRoute nmplStaticRoute : nmplStaticRoutes){
                if(nmplStaticRoute.getId() == staticRouteRequest.getId()){
                    nmplStaticRoutes.remove(nmplStaticRoute);
                    break;
                }
            }
        }
        List<String> list = new ArrayList<>();
        for(NmplStaticRoute nmplStaticRoute : nmplStaticRoutes){
            String[] split = nmplStaticRoute.getNetworkId().split("-");
            list.add(split[split.length -1]);
        }
        return list;
    }

    /**
     * 校验ID唯一
     * @param substring
     * @param staticRouteRequest
     * @return
     */
    private boolean checkID(String substring,StaticRouteRequest staticRouteRequest){
        List<String> list = selectID(staticRouteRequest);
        if(!CollectionUtils.isEmpty(list)){
            for(String s: list){
                if(s.equals(substring)){
                    return false;
                }
            }
        }
        return true;
    }


}
