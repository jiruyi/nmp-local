package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.enums.StationTypeEnum;
import com.matrictime.network.base.util.DecimalConversionUtil;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.domain.BusinessRouteDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplBusinessRouteMapper;
import com.matrictime.network.dao.model.NmplBaseStationInfo;
import com.matrictime.network.dao.model.NmplBaseStationInfoExample;
import com.matrictime.network.dao.model.NmplBusinessRoute;
import com.matrictime.network.dao.model.NmplBusinessRouteExample;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.BusinessRouteVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.BusinessRouteRequest;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2022/9/28.
 */
@Service
public class BusinessRouteDomainServiceImpl implements BusinessRouteDomainService {

    @Resource
    private NmplBusinessRouteMapper nmplBusinessRouteMapper;

    @Resource
    private NmplBaseStationInfoMapper baseStationInfoMapper;

    @Override
    public int insert(BusinessRouteRequest businessRouteRequest) {
        NmplBusinessRoute nmplBusinessRoute = new NmplBusinessRoute();
        BeanUtils.copyProperties(businessRouteRequest,nmplBusinessRoute);
        nmplBusinessRoute.setByteNetworkId(DecimalConversionUtil.idToByteArray(businessRouteRequest.getNetworkId()));
        return nmplBusinessRouteMapper.insertSelective(nmplBusinessRoute);
    }

    @Override
    public int delete(BusinessRouteRequest businessRouteRequest) {
        NmplBusinessRouteExample nmplBusinessRouteExample = new NmplBusinessRouteExample();
        NmplBusinessRouteExample.Criteria criteria = nmplBusinessRouteExample.createCriteria();
        criteria.andIdEqualTo(businessRouteRequest.getId());
        NmplBusinessRoute nmplBusinessRoute = new NmplBusinessRoute();
        BeanUtils.copyProperties(businessRouteRequest,nmplBusinessRoute);
        nmplBusinessRoute.setIsExist(false);
        return nmplBusinessRouteMapper.updateByExampleSelective(nmplBusinessRoute,nmplBusinessRouteExample);
    }

    @Override
    public int update(BusinessRouteRequest businessRouteRequest) {
        NmplBusinessRouteExample nmplBusinessRouteExample = new NmplBusinessRouteExample();
        NmplBusinessRouteExample.Criteria criteria = nmplBusinessRouteExample.createCriteria();
        criteria.andIdEqualTo(businessRouteRequest.getId());
        NmplBusinessRoute nmplBusinessRoute = new NmplBusinessRoute();
        BeanUtils.copyProperties(businessRouteRequest,nmplBusinessRoute);
        nmplBusinessRoute.setByteNetworkId(DecimalConversionUtil.idToByteArray(businessRouteRequest.getNetworkId()));
        return nmplBusinessRouteMapper.updateByExampleSelective(nmplBusinessRoute,nmplBusinessRouteExample);
    }

    @Override
    public PageInfo<BusinessRouteVo> select(BusinessRouteRequest businessRouteRequest) {
        List<BusinessRouteVo> list = new ArrayList<>();
        NmplBusinessRouteExample nmplBusinessRouteExample = new NmplBusinessRouteExample();
        NmplBusinessRouteExample.Criteria criteria = nmplBusinessRouteExample.createCriteria();
        nmplBusinessRouteExample.setOrderByClause("update_time desc");
        if(!StringUtils.isEmpty(businessRouteRequest.getBusinessType())){
            criteria.andBusinessTypeLike("%"+businessRouteRequest.getBusinessType()+"%");
        }
        if(!StringUtils.isEmpty(businessRouteRequest.getIp())){
            criteria.andIpLike("%"+businessRouteRequest.getIp()+"%");
        }
        criteria.andIsExistEqualTo(true);
        //分页查询数据
        Page page = PageHelper.startPage(businessRouteRequest.getPageNo(),businessRouteRequest.getPageSize());
        List<NmplBusinessRoute> nmplBusinessRoutes = nmplBusinessRouteMapper.selectByExample(nmplBusinessRouteExample);
        PageInfo<BusinessRouteVo> pageInfo = new PageInfo<>();
        for(NmplBusinessRoute nmplBusinessRoute: nmplBusinessRoutes){
            BusinessRouteVo businessRouteVo = new BusinessRouteVo();
            BeanUtils.copyProperties(nmplBusinessRoute,businessRouteVo);
            list.add(businessRouteVo);
        }
        pageInfo.setList(list);
        pageInfo.setCount((int) page.getTotal());
        pageInfo.setPages(page.getPages());
        return pageInfo;
    }

    @Override
    public BaseStationInfoResponse selectBaseStation(BaseStationInfoRequest baseStationInfoRequest) {
        BaseStationInfoResponse baseStationInfoResponse = new BaseStationInfoResponse();
        List<BaseStationInfoVo> list = new ArrayList<>();
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = nmplBaseStationInfoExample.createCriteria();
        if(StationTypeEnum.INSIDE.getCode().equals(baseStationInfoRequest.getStationType())){
            criteria.andStationTypeEqualTo(baseStationInfoRequest.getStationType());
        }
        criteria.andIsExistEqualTo(true);
        List<NmplBaseStationInfo> nmplBaseStationInfos = baseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        for(NmplBaseStationInfo nmplBaseStationInfo: nmplBaseStationInfos){
            BaseStationInfoVo baseStationInfoVo = new BaseStationInfoVo();
            BeanUtils.copyProperties(nmplBaseStationInfo,baseStationInfoVo);
            list.add(baseStationInfoVo);
        }
        baseStationInfoResponse.setBaseStationInfoList(list);
        return baseStationInfoResponse;
    }
}
