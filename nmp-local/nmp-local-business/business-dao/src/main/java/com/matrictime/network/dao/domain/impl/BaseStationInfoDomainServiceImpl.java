package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.dao.domain.BaseStationInfoDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.StationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.response.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
@Slf4j
public class BaseStationInfoDomainServiceImpl implements BaseStationInfoDomainService {
    @Resource
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
    public PageInfo<BaseStationInfoVo> selectBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        Page page = PageHelper.startPage(baseStationInfoRequest.getPageNo(),baseStationInfoRequest.getPageSize());
        List<BaseStationInfoVo> baseStationInfoVoList = nmplBaseStationInfoMapper.selectBaseStationInfo(baseStationInfoRequest);
        PageInfo<BaseStationInfoVo> pageResult =  new PageInfo<>();
        pageResult.setList(baseStationInfoVoList);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return  pageResult;
    }

    @Override
    public List<BaseStationInfoVo> selectLinkBaseStationInfo(BaseStationInfoRequest baseStationInfoRequest) {
        return nmplBaseStationInfoMapper.selectBaseStationInfo(baseStationInfoRequest);
    }

    @Override
    public List<BaseStationInfoVo> selectBaseStationBatch(List<String> list) {
        return nmplBaseStationInfoMapper.selectBaseStationBatch(list);
    }

    @Override
    public StationVo selectDeviceId(BaseStationInfoRequest baseStationInfoRequest) {
        return nmplBaseStationInfoMapper.selectDeviceId(baseStationInfoRequest);
    }

    @Override
    public BaseStationInfoVo selectByNetworkId(BaseStationInfoRequest baseStationInfoRequest) {
        return nmplBaseStationInfoMapper.selectByNetworkId(baseStationInfoRequest);
    }

    @Override
    public List<BaseStationInfoVo> selectByOperatorId(BaseStationInfoRequest baseStationInfoRequest) {
        return nmplBaseStationInfoMapper.selectByOperatorId(baseStationInfoRequest);
    }

    @Override
    public PageInfo<BaseStationInfoVo> selectBaseStationList(BaseStationInfoRequest baseStationInfoRequest) {
        Page page = PageHelper.startPage(baseStationInfoRequest.getPageNo(),baseStationInfoRequest.getPageSize());
        List<BaseStationInfoVo> baseStationInfoVoList = nmplBaseStationInfoMapper.selectBaseStationList(baseStationInfoRequest);
        PageInfo<BaseStationInfoVo> pageResult =  new PageInfo<>();
        pageResult.setList(baseStationInfoVoList);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return  pageResult;
    }

}