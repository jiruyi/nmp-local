package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.enums.DeviceStatusEnum;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.dao.domain.CompanyInfoDomainService;
import com.matrictime.network.dao.domain.DeviceExtraInfoDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceExtraInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.model.NmplBaseStationInfo;
import com.matrictime.network.dao.model.NmplDeviceExtraInfo;
import com.matrictime.network.dao.model.NmplDeviceExtraInfoExample;
import com.matrictime.network.dao.model.NmplDeviceInfo;
import com.matrictime.network.dao.model.extend.NmplDeviceInfoExt;
import com.matrictime.network.modelVo.DeviceExtraVo;
import com.matrictime.network.modelVo.NmplDeviceInfoExtVo;
import com.matrictime.network.request.DeviceExtraInfoRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DeviceExtraInfoDomainServiceImpl implements DeviceExtraInfoDomainService {

    @Resource
    NmplDeviceExtraInfoMapper nmplDeviceExtraInfoMapper;

    @Resource
    NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Resource
    NmplDeviceInfoMapper nmplDeviceInfoMapper;


    @Override
    public int insert(NmplDeviceExtraInfo nmplDeviceExtraInfo) {
        //入参判断
        if(nmplDeviceExtraInfo.getStationNetworkId() != null ||
                !"".equals(nmplDeviceExtraInfo.getStationNetworkId())){
            NmplDeviceExtraInfoExample nmplDeviceExtraInfoExample =new NmplDeviceExtraInfoExample();
            nmplDeviceExtraInfoExample.createCriteria().andIsExistEqualTo(true).
                    andStationNetworkIdEqualTo(nmplDeviceExtraInfo.getStationNetworkId()).andRelDeviceIdEqualTo(nmplDeviceExtraInfo.getRelDeviceId());
            List<NmplDeviceExtraInfo> nmplDeviceExtraInfos = nmplDeviceExtraInfoMapper.selectByExample(nmplDeviceExtraInfoExample);
            if(!CollectionUtils.isEmpty(nmplDeviceExtraInfos)){
                throw new SystemException("设备id重复插入");
            }
        }else {
            throw new SystemException("设备id为空");
        }
        return nmplDeviceExtraInfoMapper.insertSelective(nmplDeviceExtraInfo);
    }

    @Override
    public PageInfo<DeviceExtraVo> selectByCondition(DeviceExtraInfoRequest deviceExtraInfoRequest) {
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(null);
        List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(null);
        List<String> ids = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        for (NmplDeviceInfo nmplDeviceInfo : nmplDeviceInfos) {
            map.put(nmplDeviceInfo.getDeviceId(),nmplDeviceInfo.getDeviceName());
            if(deviceExtraInfoRequest.getRelDeviceName()!=null && nmplDeviceInfo.getDeviceName().contains(deviceExtraInfoRequest.getRelDeviceName())){
                ids.add(nmplDeviceInfo.getDeviceId());
            }
        }
        for (NmplBaseStationInfo nmplBaseStationInfo : nmplBaseStationInfos) {
            map.put(nmplBaseStationInfo.getStationId(),nmplBaseStationInfo.getStationName());
            if(deviceExtraInfoRequest.getRelDeviceName()!=null && nmplBaseStationInfo.getStationName().contains(deviceExtraInfoRequest.getRelDeviceName())){
                ids.add(nmplBaseStationInfo.getStationId());
            }
        }

        NmplDeviceExtraInfoExample deviceExtraInfoExample = new NmplDeviceExtraInfoExample();
        NmplDeviceExtraInfoExample.Criteria criteria = deviceExtraInfoExample.createCriteria();
        if(deviceExtraInfoRequest.getRelDeviceName() != null){
            if(ids.size()!=0){
                criteria.andRelDeviceIdIn(ids);
            }else {
                PageInfo pageInfo = new PageInfo<>();
                pageInfo.setList(new ArrayList<>());
                return pageInfo;
            }
        }
        if(deviceExtraInfoRequest.getDeviceName() != null){
            criteria.andDeviceNameLike("%"+deviceExtraInfoRequest.getDeviceName()+"%");
        }
        if(deviceExtraInfoRequest.getDeviceType() != null){
            criteria.andDeviceTypeEqualTo(deviceExtraInfoRequest.getDeviceType());
        }
        if(deviceExtraInfoRequest.getStationNetworkId() != null){
            criteria.andStationNetworkIdLike("%"+deviceExtraInfoRequest.getStationNetworkId()+"%");
        }
        criteria.andIsExistEqualTo(true);
        Page page = PageHelper.startPage(deviceExtraInfoRequest.getPageNo(),deviceExtraInfoRequest.getPageSize());
        deviceExtraInfoExample.setOrderByClause("create_time desc");
        List<NmplDeviceExtraInfo> nmplDeviceExtraInfos = nmplDeviceExtraInfoMapper.selectByExample(deviceExtraInfoExample);
        PageInfo<DeviceExtraVo> pageResult =  new PageInfo<>();
        List<DeviceExtraVo> list = new ArrayList<>();

        for (NmplDeviceExtraInfo nmplDeviceExtraInfo : nmplDeviceExtraInfos) {
            DeviceExtraVo deviceExtraVo = new DeviceExtraVo();
            BeanUtils.copyProperties(nmplDeviceExtraInfo,deviceExtraVo);
            deviceExtraVo.setRelDeviceName(map.get(deviceExtraVo.getRelDeviceId()));
            list.add(deviceExtraVo);
        }
        pageResult.setList(list);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return pageResult;
    }


    @Override
    public int update(NmplDeviceExtraInfo nmplDeviceExtraInfo) {
        if(nmplDeviceExtraInfo.getId() != null){
            return nmplDeviceExtraInfoMapper.updateByPrimaryKeySelective(nmplDeviceExtraInfo);
        }else {
            throw new SystemException("id字段为空");
        }
    }

    @Override
    public int delete(NmplDeviceExtraInfo nmplDeviceExtraInfo) {
        if(nmplDeviceExtraInfo.getId() != null){
            nmplDeviceExtraInfo.setIsExist(false);
            return nmplDeviceExtraInfoMapper.updateByPrimaryKeySelective(nmplDeviceExtraInfo);
        }else {
            throw new SystemException("id字段为空");
        }
    }

    @Override
    public List<NmplDeviceInfoExtVo> selectDevices(DeviceExtraInfoRequest deviceExtraInfoRequest) {
        return nmplDeviceExtraInfoMapper.selectDevices(deviceExtraInfoRequest);
    }

    @Override
    public List<NmplDeviceInfoExtVo> query(NmplDeviceExtraInfo nmplDeviceExtraInfo) {
        return nmplDeviceExtraInfoMapper.query(nmplDeviceExtraInfo);
    }
}























