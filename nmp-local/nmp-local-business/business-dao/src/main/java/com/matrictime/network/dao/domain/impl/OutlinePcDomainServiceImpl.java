package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.domain.OutlinePcDomainService;
import com.matrictime.network.dao.mapper.NmplOutlinePcInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplOutlinePcInfoExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.modelVo.NmplOutlinePcInfoVo;
import com.matrictime.network.request.OutlinePcReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.util.SnowFlake;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OutlinePcDomainServiceImpl implements OutlinePcDomainService {
    @Resource
    NmplOutlinePcInfoMapper nmplOutlinePcInfoMapper;

    @Resource
    NmplOutlinePcInfoExtMapper nmplOutlinePcInfoExtMapper;


    @Override
    public Integer save(OutlinePcReq outlinePcReq) {
        if(outlinePcReq.getStationNetworkId()!=null){
            NmplOutlinePcInfoExample nmplOutlinePcInfoExample = new NmplOutlinePcInfoExample();
            nmplOutlinePcInfoExample.createCriteria()
                    .andStationNetworkIdEqualTo(outlinePcReq.getStationNetworkId()).andIsExistEqualTo(true);
            List<NmplOutlinePcInfo> nmplOutlinePcInfos = nmplOutlinePcInfoMapper.selectByExample(nmplOutlinePcInfoExample);
            if(!CollectionUtils.isEmpty(nmplOutlinePcInfos)){
                throw new SystemException("设备id重复");
            }
        }else {
            throw new SystemException("缺失设备id");
        }
        outlinePcReq.setDeviceId(SnowFlake.nextId_String());
        NmplOutlinePcInfo nmplOutlinePcInfo = new NmplOutlinePcInfo();
        BeanUtils.copyProperties(outlinePcReq,nmplOutlinePcInfo);
        return  nmplOutlinePcInfoMapper.insertSelective(nmplOutlinePcInfo);
    }

    @Override
    public Integer delete(OutlinePcReq outlinePcReq) {
        if(outlinePcReq.getId()==null){
            throw new SystemException("id缺失");
        }
        outlinePcReq.setIsExist(false);
        NmplOutlinePcInfo nmplOutlinePcInfo = new NmplOutlinePcInfo();
        BeanUtils.copyProperties(outlinePcReq,nmplOutlinePcInfo);
        return  nmplOutlinePcInfoMapper.updateByPrimaryKeySelective(nmplOutlinePcInfo);
    }

    @Override
    public PageInfo<NmplOutlinePcInfoVo> query(OutlinePcReq outlinePcReq) {
        NmplOutlinePcInfoExample nmplOutlinePcInfoExample = new NmplOutlinePcInfoExample();
        NmplOutlinePcInfoExample.Criteria criteria = nmplOutlinePcInfoExample.createCriteria();
        if(outlinePcReq.getDeviceId()!=null){
            criteria.andDeviceIdEqualTo(outlinePcReq.getDeviceId());
        }
        if(outlinePcReq.getDeviceName()!=null){
            criteria.andDeviceNameLike("%"+outlinePcReq.getDeviceName()+"%");
        }
        if(outlinePcReq.getStartTime()!=null){
            criteria.andCreateTimeGreaterThan(outlinePcReq.getStartTime());
        }
        if(outlinePcReq.getEndTime()!=null){
            criteria.andCreateTimeLessThan(outlinePcReq.getEndTime());
        }
        if(outlinePcReq.getStationNetworkId()!=null){
            criteria.andStationNetworkIdLike("%"+outlinePcReq.getStationNetworkId()+"%");
        }
        criteria.andIsExistEqualTo(true);
        nmplOutlinePcInfoExample.setOrderByClause("create_time desc");
        Page page = PageHelper.startPage(outlinePcReq.getPageNo(),outlinePcReq.getPageSize());
        List<NmplOutlinePcInfo> nmplOutlinePcInfos = nmplOutlinePcInfoMapper.selectByExample(nmplOutlinePcInfoExample);

        List<NmplOutlinePcInfoVo> list = new ArrayList<>();
        for (NmplOutlinePcInfo nmplOutlinePcInfo : nmplOutlinePcInfos) {
            NmplOutlinePcInfoVo infoVo = new NmplOutlinePcInfoVo();
            BeanUtils.copyProperties(nmplOutlinePcInfo,infoVo);
            list.add(infoVo);
        }
        PageInfo<NmplOutlinePcInfoVo> pageResult =  new PageInfo<>();
        pageResult.setList(list);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return pageResult;
    }

    @Override
    public Integer modify(OutlinePcReq outlinePcReq) {
        if(outlinePcReq.getId()==null){
            throw new SystemException("id缺失");
        }

        if(outlinePcReq.getStationNetworkId()!=null){
            NmplOutlinePcInfo info = nmplOutlinePcInfoMapper.selectByPrimaryKey(outlinePcReq.getId());
            NmplOutlinePcInfoExample nmplOutlinePcInfoExample = new NmplOutlinePcInfoExample();
            nmplOutlinePcInfoExample.createCriteria()
                    .andStationNetworkIdEqualTo(outlinePcReq.getStationNetworkId()).andIsExistEqualTo(true);
            List<NmplOutlinePcInfo> nmplOutlinePcInfos = nmplOutlinePcInfoMapper.selectByExample(nmplOutlinePcInfoExample);
            if(!CollectionUtils.isEmpty(nmplOutlinePcInfos)){
                if(!nmplOutlinePcInfos.get(0).getStationNetworkId().equals(info.getStationNetworkId())){
                    throw new SystemException("设备id重复");
                }
            }
        }
        NmplOutlinePcInfo nmplOutlinePcInfo = new NmplOutlinePcInfo();
        BeanUtils.copyProperties(outlinePcReq,nmplOutlinePcInfo);
        return nmplOutlinePcInfoMapper.updateByPrimaryKeySelective(nmplOutlinePcInfo);
    }

    @Override
    @Transactional
    public Integer batchInsert(List<NmplOutlinePcInfo> nmplOutlinePcInfoList) {
        NmplOutlinePcInfoExample nmplOutlinePcInfoExample = new NmplOutlinePcInfoExample();
        nmplOutlinePcInfoExample.createCriteria().andIsExistEqualTo(true);
        List<NmplOutlinePcInfo> pcInfos = nmplOutlinePcInfoMapper.selectByExample(nmplOutlinePcInfoExample);
        Set<String> stationNetworkIdSet = new HashSet<>();
        for (NmplOutlinePcInfo pcInfo : pcInfos) {
            stationNetworkIdSet.add(pcInfo.getStationNetworkId());
        }
        for (NmplOutlinePcInfo nmplOutlinePcInfo : nmplOutlinePcInfoList) {
            if(stationNetworkIdSet.contains(nmplOutlinePcInfo.getStationNetworkId())){
                throw new SystemException(ErrorMessageContants.DEVICEID_REPEAT+" 设备id:"+nmplOutlinePcInfo.getStationNetworkId());
            }
            stationNetworkIdSet.add(nmplOutlinePcInfo.getStationNetworkId());
        }
        return nmplOutlinePcInfoExtMapper.batchInsert(nmplOutlinePcInfoList);
    }
}
