package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.util.DecimalConversionUtil;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.dao.domain.InternetRouteDomainService;
import com.matrictime.network.dao.mapper.NmplInternetRouteMapper;
import com.matrictime.network.dao.mapper.extend.NmplInternetRouteExtMapper;
import com.matrictime.network.dao.model.NmplInternetRoute;
import com.matrictime.network.dao.model.NmplInternetRouteExample;
import com.matrictime.network.modelVo.BusinessRouteVo;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.InternetRouteVo;
import com.matrictime.network.request.InternetRouteRequest;
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
 * @date 2022/9/29.
 */
@Service
public class InternetRouteDomainServiceImpl implements InternetRouteDomainService {

    @Resource
    private NmplInternetRouteMapper nmplInternetRouteMapper;

    @Resource
    private NmplInternetRouteExtMapper nmplInternetRouteExtMapper;

    @Override
    public int insert(InternetRouteRequest internetRouteRequest) {
        NmplInternetRoute nmplInternetRoute = new NmplInternetRoute();
        BeanUtils.copyProperties(internetRouteRequest,nmplInternetRoute);
//        nmplInternetRoute.setByteNetworkId(DecimalConversionUtil.idToByteArray(internetRouteRequest.getNetworkId()));
        return nmplInternetRouteMapper.insertSelective(nmplInternetRoute);
    }

    @Override
    public int delete(InternetRouteRequest internetRouteRequest) {
        NmplInternetRouteExample nmplInternetRouteExample = constructUpdateCondition(internetRouteRequest);
        NmplInternetRoute nmplInternetRoute = constructUpdateDate(internetRouteRequest);
        nmplInternetRoute.setIsExist(false);
        return nmplInternetRouteMapper.updateByExampleSelective(nmplInternetRoute,nmplInternetRouteExample);
    }

    @Override
    public int update(InternetRouteRequest internetRouteRequest) {
        NmplInternetRouteExample nmplInternetRouteExample = constructUpdateCondition(internetRouteRequest);
        NmplInternetRoute nmplInternetRoute = constructUpdateDate(internetRouteRequest);
//        nmplInternetRoute.setByteNetworkId(DecimalConversionUtil.idToByteArray(internetRouteRequest.getNetworkId()));
        return nmplInternetRouteMapper.updateByExampleSelective(nmplInternetRoute,nmplInternetRouteExample);
    }

    @Override
    public PageInfo<InternetRouteVo> select(InternetRouteRequest internetRouteRequest) {
        List<InternetRouteVo> list = new ArrayList<>();
        NmplInternetRouteExample nmplInternetRouteExample = new NmplInternetRouteExample();
        NmplInternetRouteExample.Criteria criteria = nmplInternetRouteExample.createCriteria();
        NmplInternetRouteExample.Criteria criteria1 = nmplInternetRouteExample.createCriteria();
        nmplInternetRouteExample.setOrderByClause("update_time desc");
        if(!StringUtils.isEmpty(internetRouteRequest.getDeviceId())){
            criteria.andDeviceIdEqualTo(internetRouteRequest.getDeviceId());
            criteria1.andDeviceIdEqualTo(internetRouteRequest.getDeviceId());
        }
        if(!StringUtils.isEmpty(internetRouteRequest.getDeviceType())){
            criteria.andDeviceTypeEqualTo(internetRouteRequest.getDeviceType());
            criteria1.andDeviceTypeEqualTo(internetRouteRequest.getDeviceType());
        }
        if(!StringUtils.isEmpty(internetRouteRequest.getDeviceName())){
            criteria.andDeviceNameEqualTo(internetRouteRequest.getDeviceName());
            criteria1.andDeviceNameEqualTo(internetRouteRequest.getDeviceName());
        }
        criteria1.andIsExistEqualTo(true);
        criteria.andIsExistEqualTo(true);
        nmplInternetRouteExample.or(criteria1);
        //分页查询数据
        Page page = PageHelper.startPage(internetRouteRequest.getPageNo(),internetRouteRequest.getPageSize());
        List<NmplInternetRoute> nmplInternetRoutes = nmplInternetRouteMapper.selectByExample(nmplInternetRouteExample);
        PageInfo<InternetRouteVo> pageInfo = new PageInfo<>();
        for(NmplInternetRoute nmplInternetRoute: nmplInternetRoutes){
            InternetRouteVo internetRouteVo = new InternetRouteVo();
            BeanUtils.copyProperties(nmplInternetRoute,internetRouteVo);
            list.add(internetRouteVo);
        }
        pageInfo.setList(list);
        pageInfo.setCount((int) page.getTotal());
        pageInfo.setPages(page.getPages());
        return pageInfo;
    }

    /**
     * 查询设备
     * @param internetRouteRequest
     * @return
     */
    @Override
    public List<DeviceInfoVo> selectDevice(InternetRouteRequest internetRouteRequest) {
        List<DeviceInfoVo> deviceInfoVos = nmplInternetRouteExtMapper.selectDevice(internetRouteRequest);
        return deviceInfoVos;
    }


    /**
     * 构造跟新数据
     * @param internetRouteRequest
     * @return
     */
    private NmplInternetRoute constructUpdateDate(InternetRouteRequest internetRouteRequest){
        NmplInternetRoute nmplInternetRoute = new NmplInternetRoute();
        BeanUtils.copyProperties(internetRouteRequest,nmplInternetRoute);
        return nmplInternetRoute;
    }

    /**
     * 构造更新条件
     * @param internetRouteRequest
     * @return
     */
    private NmplInternetRouteExample constructUpdateCondition(InternetRouteRequest internetRouteRequest){
        NmplInternetRouteExample nmplInternetRouteExample = new NmplInternetRouteExample();
        NmplInternetRouteExample.Criteria criteria = nmplInternetRouteExample.createCriteria();
        criteria.andIdEqualTo(internetRouteRequest.getId());
        return nmplInternetRouteExample;
    }
}
