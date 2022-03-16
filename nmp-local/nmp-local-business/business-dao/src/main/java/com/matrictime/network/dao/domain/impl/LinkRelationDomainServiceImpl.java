package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.dao.domain.LinkRelationDomainService;
import com.matrictime.network.dao.mapper.NmplLinkRelationMapper;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.LinkRelationVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.DeviceInfoRequest;
import com.matrictime.network.request.LinkRelationRequest;
import com.matrictime.network.response.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class LinkRelationDomainServiceImpl implements LinkRelationDomainService {

    @Resource
    private NmplLinkRelationMapper nmplLinkRelationMapper;

    @Override
    public int insertLinkRelation(LinkRelationRequest linkRelationRequest) {
        return nmplLinkRelationMapper.insertLinkRelation(linkRelationRequest);
    }

    @Override
    public int deleteLinkRelation(LinkRelationRequest linkRelationRequest) {
        return nmplLinkRelationMapper.deleteLinkRelation(linkRelationRequest);
    }

    @Override
    public int updateLinkRelation(LinkRelationRequest linkRelationRequest) {
        return nmplLinkRelationMapper.updateLinkRelation(linkRelationRequest);
    }

    @Override
    public PageInfo<LinkRelationVo> selectLinkRelation(LinkRelationRequest linkRelationRequest) {
        Page page = PageHelper.startPage(linkRelationRequest.getPageNo(),linkRelationRequest.getPageSize());
        List<LinkRelationVo> linkRelationVos = nmplLinkRelationMapper.selectLinkRelation(linkRelationRequest);
        PageInfo<LinkRelationVo> pageResult =  new PageInfo<>();
        pageResult.setList(linkRelationVos);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return  pageResult;
    }

    @Override
    public List<BaseStationInfoVo> selectLinkRelationStation(BaseStationInfoRequest baseStationInfoRequest) {
        return nmplLinkRelationMapper.selectLinkRelationStation(baseStationInfoRequest);
    }

    @Override
    public List<DeviceInfoVo> selectLinkRelationDevice(DeviceInfoRequest deviceInfoRequest) {
        return nmplLinkRelationMapper.selectLinkRelationDevice(deviceInfoRequest);
    }


}