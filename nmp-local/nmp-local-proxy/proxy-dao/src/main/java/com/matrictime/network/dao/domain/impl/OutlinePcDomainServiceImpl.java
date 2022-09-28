package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.domain.OutlinePcDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplOutlinePcInfoMapper;
import com.matrictime.network.dao.mapper.extend.BaseStationInfoMapper;
import com.matrictime.network.dao.model.NmplBaseStationInfo;
import com.matrictime.network.dao.model.NmplBaseStationInfoExample;
import com.matrictime.network.dao.model.NmplOutlinePcInfo;
import com.matrictime.network.dao.model.NmplOutlinePcInfoExample;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.OutlinePcVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.OutlinePcListRequest;
import com.matrictime.network.request.OutlinePcReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2022/9/15.
 */
@Service
@Slf4j
public class OutlinePcDomainServiceImpl implements OutlinePcDomainService {

    @Resource
    private NmplOutlinePcInfoMapper outlinePcInfoMapper;

    @Resource
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Override
    public int updateOutlinePc(OutlinePcReq outlinePcReq) {
        NmplOutlinePcInfoExample nmplOutlinePcInfoExample = new NmplOutlinePcInfoExample();
        NmplOutlinePcInfoExample.Criteria criteria = nmplOutlinePcInfoExample.createCriteria();
        NmplOutlinePcInfo nmplOutlinePcInfo = new NmplOutlinePcInfo();
        BeanUtils.copyProperties(outlinePcReq,nmplOutlinePcInfo);
        //查询数据库判断数据库中是否有该数据
        NmplOutlinePcInfo outlinePcInfo = outlinePcInfoMapper.selectByPrimaryKey(nmplOutlinePcInfo.getId());
        if(!ObjectUtils.isEmpty(outlinePcInfo)){
            criteria.andIdEqualTo(nmplOutlinePcInfo.getId());
            return outlinePcInfoMapper.updateByExampleSelective(nmplOutlinePcInfo,nmplOutlinePcInfoExample);
        }else {
            return outlinePcInfoMapper.insertSelective(nmplOutlinePcInfo);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int batchInsertOutlinePc(OutlinePcListRequest listRequest) {
        for (OutlinePcReq outlinePcReq : listRequest.getList()){
            NmplOutlinePcInfo nmplOutlinePcInfo = new NmplOutlinePcInfo();
            BeanUtils.copyProperties(outlinePcReq,nmplOutlinePcInfo);
            int flag = outlinePcInfoMapper.insertSelective(nmplOutlinePcInfo);
            if(flag != NumberUtils.INTEGER_ONE){
                throw new RuntimeException("批量插入发生错误");
            }
        }
        return 1;
    }

    @Override
    public int insertOutlinePc(OutlinePcReq outlinePcReq) {
        NmplOutlinePcInfo nmplOutlinePcInfo = new NmplOutlinePcInfo();
        BeanUtils.copyProperties(outlinePcReq,nmplOutlinePcInfo);
        return outlinePcInfoMapper.insertSelective(nmplOutlinePcInfo);
    }

    @Override
    public List<OutlinePcVo> selectOutlinePc(OutlinePcReq outlinePcReq) {
        List<OutlinePcVo> list = new ArrayList<>();
        NmplOutlinePcInfoExample nmplOutlinePcInfoExample = new NmplOutlinePcInfoExample();
        NmplOutlinePcInfoExample.Criteria criteria = nmplOutlinePcInfoExample.createCriteria();
        if(StringUtils.isEmpty(outlinePcReq.getDeviceId())){
            throw new RuntimeException(ErrorMessageContants.DEVICE_ID_IS_NULL_MSG);
        }
        criteria.andDeviceIdEqualTo(outlinePcReq.getDeviceId());
        List<NmplOutlinePcInfo> nmplOutlinePcInfos = outlinePcInfoMapper.selectByExample(nmplOutlinePcInfoExample);
        for(NmplOutlinePcInfo nmplOutlinePcInfo: nmplOutlinePcInfos){
            OutlinePcVo outlinePcVo = new OutlinePcVo();
            BeanUtils.copyProperties(nmplOutlinePcInfo,outlinePcVo);
            list.add(outlinePcVo);
        }
        return list;
    }

    @Override
    public List<BaseStationInfoVo> selectBaseStation(BaseStationInfoRequest baseStationInfoRequest) {
        List<BaseStationInfoVo> list = new ArrayList<>();
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = nmplBaseStationInfoExample.createCriteria();
        if(StringUtils.isEmpty(baseStationInfoRequest.getStationId())){
            throw new RuntimeException(ErrorMessageContants.DEVICE_ID_IS_NULL_MSG);
        }
        criteria.andStationIdEqualTo(baseStationInfoRequest.getStationId());
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        for(NmplBaseStationInfo nmplBaseStationInfo: nmplBaseStationInfos){
            BaseStationInfoVo baseStationInfoVo = new BaseStationInfoVo();
            BeanUtils.copyProperties(nmplBaseStationInfo,baseStationInfoVo);
            list.add(baseStationInfoVo);
        }
        return list;
    }


}
