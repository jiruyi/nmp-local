package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class BaseStationInfoDomainServiceImpl implements BaseStationInfoDomainService {
    @Autowired
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Override
    public int insertBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        return nmplBaseStationInfoMapper.insertBaseStationInfo(baseStationInfoRequest);
    }

    @Override
    public int updateBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        return nmplBaseStationInfoMapper.updateBaseStationInfo(baseStationInfoRequest);
    }

    @Override
    public int deleteBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        return nmplBaseStationInfoMapper.deleteBaseStationInfo(baseStationInfoRequest);
    }

    @Override
    public List<BaseStationInfoVo> selectBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        return nmplBaseStationInfoMapper.selectBaseStationInfo(baseStationInfoRequest);
    }
}