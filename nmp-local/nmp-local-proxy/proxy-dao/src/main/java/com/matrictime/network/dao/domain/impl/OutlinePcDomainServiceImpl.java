package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.OutlinePcDomainService;
import com.matrictime.network.dao.mapper.NmplOutlinePcInfoMapper;
import com.matrictime.network.dao.model.NmplOutlinePcInfo;
import com.matrictime.network.dao.model.NmplOutlinePcInfoExample;
import com.matrictime.network.request.OutlinePcListRequest;
import com.matrictime.network.request.OutlinePcReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2022/9/15.
 */
@Service
@Slf4j
public class OutlinePcDomainServiceImpl implements OutlinePcDomainService {

    @Resource
    private NmplOutlinePcInfoMapper outlinePcInfoMapper;

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
            if(flag != 1){
                throw new RuntimeException("批量插入发生错误");
            }
        }
        return 1;
    }
























}
