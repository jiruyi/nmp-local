package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.dao.domain.OutlineSorterDomainService;
import com.matrictime.network.dao.mapper.NmplOutlineSorterInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplOutlineSorterInfoExtMapper;
import com.matrictime.network.dao.model.NmplOutlineSorterInfo;
import com.matrictime.network.dao.model.NmplOutlineSorterInfoExample;
import com.matrictime.network.modelVo.NmplOutlinePcInfoVo;
import com.matrictime.network.modelVo.NmplOutlineSorterInfoVo;
import com.matrictime.network.request.OutlineSorterReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OutlineSorterDomainServiceImpl implements OutlineSorterDomainService {

    @Resource
    NmplOutlineSorterInfoMapper nmplOutlineSorterInfoMapper;

    @Resource
    NmplOutlineSorterInfoExtMapper nmplOutlineSorterInfoExtMapper;


    @Override
    public Integer save(OutlineSorterReq outlineSorterReq) {
        if(outlineSorterReq.getStationNetworkId()!=null){
            NmplOutlineSorterInfoExample nmplOutlineSorterInfoExample = new NmplOutlineSorterInfoExample();
            nmplOutlineSorterInfoExample.createCriteria()
                    .andStationNetworkIdEqualTo(outlineSorterReq.getStationNetworkId()).andIsExistEqualTo(true);
            List<NmplOutlineSorterInfo> nmplOutlineSorterInfoList = nmplOutlineSorterInfoMapper.selectByExample(nmplOutlineSorterInfoExample);
            if(!CollectionUtils.isEmpty(nmplOutlineSorterInfoList)){
                throw new SystemException("设备id重复");
            }
        }else {
            throw new SystemException("编码缺失");
        }
        outlineSorterReq.setDeviceId(SnowFlake.nextId_String());
        NmplOutlineSorterInfo nmplOutlineSorterInfo = new NmplOutlineSorterInfo();
        BeanUtils.copyProperties(outlineSorterReq,nmplOutlineSorterInfo);
        return  nmplOutlineSorterInfoMapper.insertSelective(nmplOutlineSorterInfo);
    }

    @Override
    public Integer delete(OutlineSorterReq outlineSorterReq) {
        if(outlineSorterReq.getId()==null){
            throw new SystemException("id缺失");
        }
        outlineSorterReq.setIsExist(false);
        NmplOutlineSorterInfo nmplOutlineSorterInfo = new NmplOutlineSorterInfo();
        BeanUtils.copyProperties(outlineSorterReq,nmplOutlineSorterInfo);
        return nmplOutlineSorterInfoMapper.updateByPrimaryKeySelective(nmplOutlineSorterInfo);
    }

    @Override
    public PageInfo<NmplOutlineSorterInfoVo> query(OutlineSorterReq outlineSorterReq){
        NmplOutlineSorterInfoExample nmplOutlineSorterInfoExample = new NmplOutlineSorterInfoExample();
        NmplOutlineSorterInfoExample.Criteria criteria = nmplOutlineSorterInfoExample.createCriteria();
        if(outlineSorterReq.getDeviceId()!=null){
            criteria.andDeviceIdEqualTo(outlineSorterReq.getDeviceId());
        }
        if(outlineSorterReq.getStationNetworkId()!=null){
            criteria.andStationNetworkIdLike("%"+outlineSorterReq.getStationNetworkId()+"%");
        }
        if(outlineSorterReq.getDeviceName()!=null){
            criteria.andDeviceNameLike("%"+outlineSorterReq.getDeviceName()+"%");
        }
        if(outlineSorterReq.getStartTime()!=null){
            criteria.andCreateTimeGreaterThan(outlineSorterReq.getStartTime());
        }
        if(outlineSorterReq.getEndTime()!=null){
            criteria.andCreateTimeLessThan(outlineSorterReq.getEndTime());
        }
        criteria.andIsExistEqualTo(true);
        nmplOutlineSorterInfoExample.setOrderByClause("create_time desc");
        Page page = PageHelper.startPage(outlineSorterReq.getPageNo(),outlineSorterReq.getPageSize());
        List<NmplOutlineSorterInfo> nmplOutlineSorterInfoList = nmplOutlineSorterInfoMapper.selectByExample(nmplOutlineSorterInfoExample);
        List<NmplOutlineSorterInfoVo> list = new ArrayList<>();
        for (NmplOutlineSorterInfo nmplOutlineSorterInfo : nmplOutlineSorterInfoList) {
            NmplOutlineSorterInfoVo infoVo = new NmplOutlineSorterInfoVo();
            BeanUtils.copyProperties(nmplOutlineSorterInfo,infoVo);
            list.add(infoVo);
        }
        //list = list.stream().sorted(Comparator.comparing(NmplOutlineSorterInfoVo::getCreateTime).reversed()).collect(Collectors.toList());
        PageInfo<NmplOutlineSorterInfoVo> pageResult =  new PageInfo<>();
        pageResult.setList(list);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return pageResult;
    }

    @Override
    public Integer modify(OutlineSorterReq outlineSorterReq) {
        if(outlineSorterReq.getId()==null){
            throw new SystemException("id缺失");
        }
        NmplOutlineSorterInfo info = nmplOutlineSorterInfoMapper.selectByPrimaryKey(outlineSorterReq.getId());
        if(outlineSorterReq.getStationNetworkId()!=null){
            NmplOutlineSorterInfoExample nmplOutlineSorterInfoExample = new NmplOutlineSorterInfoExample();
            nmplOutlineSorterInfoExample.createCriteria()
                    .andStationNetworkIdEqualTo(outlineSorterReq.getStationNetworkId()).andIsExistEqualTo(true);
            List<NmplOutlineSorterInfo> nmplOutlineSorterInfoList = nmplOutlineSorterInfoMapper.selectByExample(nmplOutlineSorterInfoExample);
            if(!CollectionUtils.isEmpty(nmplOutlineSorterInfoList)){
                if(!nmplOutlineSorterInfoList.get(0).getStationNetworkId().equals(info.getStationNetworkId())){
                    throw new SystemException("设备id重复");
                }
            }
        }
        NmplOutlineSorterInfo nmplOutlineSorterInfo = new NmplOutlineSorterInfo();
        BeanUtils.copyProperties(outlineSorterReq,nmplOutlineSorterInfo);
        return nmplOutlineSorterInfoMapper.updateByPrimaryKeySelective(nmplOutlineSorterInfo);
    }

    @Override
    @Transactional
    public Integer batchInsert(List<NmplOutlineSorterInfo> nmplOutlineSorterInfoList) {
        return nmplOutlineSorterInfoExtMapper.batchInsert(nmplOutlineSorterInfoList);
    }

    @Override
    public NmplOutlineSorterInfo auth(OutlineSorterReq outlineSorterReq) {
        if(outlineSorterReq.getStationNetworkId()==null){
            throw new SystemException("stationNetworkId缺失");
        }
        NmplOutlineSorterInfoExample nmplOutlineSorterInfoExample = new NmplOutlineSorterInfoExample();
        nmplOutlineSorterInfoExample.createCriteria()
                .andStationNetworkIdEqualTo(outlineSorterReq.getStationNetworkId()).andIsExistEqualTo(true);
        List<NmplOutlineSorterInfo> nmplOutlineSorterInfoList = nmplOutlineSorterInfoMapper.selectByExample(nmplOutlineSorterInfoExample);
        if(CollectionUtils.isEmpty(nmplOutlineSorterInfoList)){
            throw new SystemException("无该设备id");
        }else {
            return nmplOutlineSorterInfoList.get(0);
        }
    }
}
