package com.matrictime.network.dao.domain.impl;


import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.domain.OutlinePcDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplOutlinePcInfoMapper;
import com.matrictime.network.dao.mapper.NmplUpdateInfoBaseMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.OutlinePcVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.OutlinePcListRequest;
import com.matrictime.network.request.OutlinePcReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.NMPL_OUTLINE_PC_INFO;
import static com.matrictime.network.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.SYSTEM_NM;

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

    @Resource
    private NmplUpdateInfoBaseMapper nmplUpdateInfoBaseMapper;

    @Override
    public int updateOutlinePc(OutlinePcReq outlinePcReq) {
        Date createTime = new Date();
        NmplOutlinePcInfoExample nmplOutlinePcInfoExample = new NmplOutlinePcInfoExample();
        NmplOutlinePcInfoExample.Criteria criteria = nmplOutlinePcInfoExample.createCriteria();
        NmplOutlinePcInfo nmplOutlinePcInfo = new NmplOutlinePcInfo();
        BeanUtils.copyProperties(outlinePcReq,nmplOutlinePcInfo);
        nmplOutlinePcInfo.setUpdateTime(createTime);
        //查询数据库判断数据库中是否有该数据
        int i = 0;
        NmplOutlinePcInfo outlinePcInfo = outlinePcInfoMapper.selectByPrimaryKey(nmplOutlinePcInfo.getId());
        if(!ObjectUtils.isEmpty(outlinePcInfo)){
            criteria.andIdEqualTo(nmplOutlinePcInfo.getId());
            i = outlinePcInfoMapper.updateByExampleSelective(nmplOutlinePcInfo, nmplOutlinePcInfoExample);
            if(i == RETURN_SUCCESS){
                noticeUpdateInfoBase(createTime);
            }
        }else {
            i = outlinePcInfoMapper.insertSelective(nmplOutlinePcInfo);
            if(i == RETURN_SUCCESS){
                noticeAddInfoBase(createTime);
            }
        }
        return i;
    }

    private void noticeAddInfoBase(Date createTime){
        NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
        updateInfo.setTableName(NMPL_OUTLINE_PC_INFO);
        updateInfo.setOperationType(EDIT_TYPE_ADD);
        updateInfo.setCreateTime(createTime);
        updateInfo.setCreateUser(SYSTEM_NM);
        nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
    }

    private void noticeUpdateInfoBase(Date createTime){
        NmplUpdateInfoBase updateInfo = new NmplUpdateInfoBase();
        updateInfo.setTableName(NMPL_OUTLINE_PC_INFO);
        updateInfo.setOperationType(EDIT_TYPE_UPD);
        updateInfo.setCreateTime(createTime);
        updateInfo.setCreateUser(SYSTEM_NM);
        nmplUpdateInfoBaseMapper.insertSelective(updateInfo);
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
        return DataConstants.RETURN_SUCCESS;
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
        criteria.andIdEqualTo(outlinePcReq.getId());
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
        if(!ObjectUtils.isEmpty(baseStationInfoRequest.getId())){
            criteria.andIdEqualTo(baseStationInfoRequest.getId());
        }
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        for(NmplBaseStationInfo nmplBaseStationInfo: nmplBaseStationInfos){
            BaseStationInfoVo baseStationInfoVo = new BaseStationInfoVo();
            BeanUtils.copyProperties(nmplBaseStationInfo,baseStationInfoVo);
            list.add(baseStationInfoVo);
        }
        return list;
    }


}
