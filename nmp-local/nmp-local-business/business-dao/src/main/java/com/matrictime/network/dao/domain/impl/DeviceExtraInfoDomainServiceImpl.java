package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.dao.domain.DeviceExtraInfoDomainService;
import com.matrictime.network.dao.mapper.NmplDeviceExtraInfoMapper;
import com.matrictime.network.dao.model.NmplCompanyInfo;
import com.matrictime.network.dao.model.NmplDeviceExtraInfo;
import com.matrictime.network.dao.model.NmplDeviceExtraInfoExample;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.DeviceExtraVo;
import com.matrictime.network.modelVo.NmplCompanyInfoVo;
import com.matrictime.network.request.DeviceExtraInfoRequest;
import com.matrictime.network.response.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeviceExtraInfoDomainServiceImpl implements DeviceExtraInfoDomainService {

    @Resource
    NmplDeviceExtraInfoMapper nmplDeviceExtraInfoMapper;

    @Override
    public int insert(NmplDeviceExtraInfo nmplDeviceExtraInfo) {
        //入参判断
        if(nmplDeviceExtraInfo.getStationNetworkId() != null ||
                !"".equals(nmplDeviceExtraInfo.getStationNetworkId())){
            NmplDeviceExtraInfoExample nmplDeviceExtraInfoExample =new NmplDeviceExtraInfoExample();
            nmplDeviceExtraInfoExample.createCriteria().andIsExistEqualTo(true).
                    andStationNetworkIdEqualTo(nmplDeviceExtraInfo.getStationNetworkId());
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
        NmplDeviceExtraInfoExample deviceExtraInfoExample = new NmplDeviceExtraInfoExample();
        NmplDeviceExtraInfoExample.Criteria criteria = deviceExtraInfoExample.createCriteria();
        if(deviceExtraInfoRequest.getRelDeviceId() != null){
            criteria.andRelDeviceIdEqualTo(deviceExtraInfoRequest.getRelDeviceId());
        }
        if(deviceExtraInfoRequest.getDeviceName() != null){
            criteria.andDeviceNameEqualTo(deviceExtraInfoRequest.getDeviceName());
        }
        if(deviceExtraInfoRequest.getDeviceType() != null){
            criteria.andDeviceTypeEqualTo(deviceExtraInfoRequest.getDeviceType());
        }
        criteria.andIsExistEqualTo(true);
        Page page = PageHelper.startPage(deviceExtraInfoRequest.getPageNo(),deviceExtraInfoRequest.getPageSize());
        List<NmplDeviceExtraInfo> nmplDeviceExtraInfos = nmplDeviceExtraInfoMapper.selectByExample(deviceExtraInfoExample);
        PageInfo<DeviceExtraVo> pageResult =  new PageInfo<>();
        List<DeviceExtraVo> list = new ArrayList<>();
        for (NmplDeviceExtraInfo nmplDeviceExtraInfo : nmplDeviceExtraInfos) {
            DeviceExtraVo deviceExtraVo = new DeviceExtraVo();
            BeanUtils.copyProperties(nmplDeviceExtraInfo,deviceExtraVo);
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
}























