package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.mapper.extend.BaseStationInfoMapper;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Slf4j
public class BaseStationInfoDomainServiceImpl implements BaseStationInfoDomainService {

    @Resource
    private BaseStationInfoMapper baseStationInfoMapper;

    @Override
    @Transactional
    public int insertBaseStationInfo(List<BaseStationInfoVo> baseStationInfoVos) {
        return baseStationInfoMapper.batchInsert(baseStationInfoVos);
    }

    @Override
    @Transactional
    public int updateBaseStationInfo(List<BaseStationInfoVo> baseStationInfoVos) {
        return baseStationInfoMapper.batchUpdate(baseStationInfoVos);
    }

    @Override
    @Transactional
    public int deleteBaseStationInfo(List<Long> ids) {
        return baseStationInfoMapper.batchDelete(ids);
    }

}