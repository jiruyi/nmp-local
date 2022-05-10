package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.dao.domain.OutlinePcDomainService;
import com.matrictime.network.dao.mapper.NmplOutlinePcInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplOutlinePcInfoExtMapper;
import com.matrictime.network.dao.model.NmplOutlinePcInfo;
import com.matrictime.network.dao.model.NmplOutlinePcInfoExample;
import com.matrictime.network.dao.model.NmplOutlineSorterInfo;
import com.matrictime.network.dao.model.NmplOutlineSorterInfoExample;
import com.matrictime.network.modelVo.NmplOutlinePcInfoVo;
import com.matrictime.network.request.OutlinePcReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.util.SnowFlake;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class OutlinePcDomainServiceImpl implements OutlinePcDomainService {
    @Resource
    NmplOutlinePcInfoMapper nmplOutlinePcInfoMapper;

    @Resource
    NmplOutlinePcInfoExtMapper nmplOutlinePcInfoExtMapper;


    @Override
    public Integer save(OutlinePcReq outlinePcReq) {
        if(outlinePcReq.getDeviceId()!=null){
            NmplOutlinePcInfoExample nmplOutlinePcInfoExample = new NmplOutlinePcInfoExample();
            nmplOutlinePcInfoExample.createCriteria()
                    .andDeviceIdEqualTo(outlinePcReq.getDeviceId()).andIsExistEqualTo(true);
            List<NmplOutlinePcInfo> nmplOutlinePcInfos = nmplOutlinePcInfoMapper.selectByExample(nmplOutlinePcInfoExample);
            if(!CollectionUtils.isEmpty(nmplOutlinePcInfos)){
                throw new SystemException("设备id重复");
            }
        }else {
            throw new SystemException("缺失设备id");
        }
        outlinePcReq.setId(SnowFlake.nextId());
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
            criteria.andDeviceNameEqualTo(outlinePcReq.getDeviceName());
        }
        if(outlinePcReq.getStartTime()!=null){
            criteria.andCreateTimeGreaterThan(outlinePcReq.getStartTime());
        }
        if(outlinePcReq.getEndTime()!=null){
            criteria.andCreateTimeLessThan(outlinePcReq.getEndTime());
        }
        criteria.andIsExistEqualTo(true);
        Page page = PageHelper.startPage(outlinePcReq.getPageNo(),outlinePcReq.getPageSize());
        List<NmplOutlinePcInfo> nmplOutlinePcInfos = nmplOutlinePcInfoMapper.selectByExample(nmplOutlinePcInfoExample);

        List<NmplOutlinePcInfoVo> list = new ArrayList<>();
        for (NmplOutlinePcInfo nmplOutlinePcInfo : nmplOutlinePcInfos) {
            NmplOutlinePcInfoVo infoVo = new NmplOutlinePcInfoVo();
            BeanUtils.copyProperties(nmplOutlinePcInfo,infoVo);
            infoVo.setId(String.valueOf(nmplOutlinePcInfo.getId()));
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

        if(outlinePcReq.getDeviceId()!=null){
            NmplOutlinePcInfo info = nmplOutlinePcInfoMapper.selectByPrimaryKey(outlinePcReq.getId());
            NmplOutlinePcInfoExample nmplOutlinePcInfoExample = new NmplOutlinePcInfoExample();
            nmplOutlinePcInfoExample.createCriteria()
                    .andDeviceIdEqualTo(outlinePcReq.getDeviceId()).andIsExistEqualTo(true);
            List<NmplOutlinePcInfo> nmplOutlinePcInfos = nmplOutlinePcInfoMapper.selectByExample(nmplOutlinePcInfoExample);
            if(!CollectionUtils.isEmpty(nmplOutlinePcInfos)){
                if(!nmplOutlinePcInfos.get(0).getDeviceId().equals(info.getDeviceId())){
                    throw new SystemException("设备id重复");
                }
            }
        }
        NmplOutlinePcInfo nmplOutlinePcInfo = new NmplOutlinePcInfo();
        BeanUtils.copyProperties(outlinePcReq,nmplOutlinePcInfo);
        return nmplOutlinePcInfoMapper.updateByPrimaryKeySelective(nmplOutlinePcInfo);
    }

    @Override
    public Integer batchInsert(List<NmplOutlinePcInfo> nmplOutlinePcInfoList) {
        return nmplOutlinePcInfoExtMapper.batchInsert(nmplOutlinePcInfoList);
    }
}
