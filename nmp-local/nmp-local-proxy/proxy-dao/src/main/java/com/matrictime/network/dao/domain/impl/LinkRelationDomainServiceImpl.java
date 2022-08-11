package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.LinkRelationDomainService;
import com.matrictime.network.dao.mapper.extend.LinkRelationMapper;
import com.matrictime.network.modelVo.LinkRelationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class LinkRelationDomainServiceImpl implements LinkRelationDomainService {

    @Resource
    private LinkRelationMapper linkRelationMapper;

    @Override
    public int insertLinkRelation(List<LinkRelationVo> list) {
        return linkRelationMapper.batchInsert(list);
    }

    @Override
    public int updateLinkRelation(List<LinkRelationVo> list) {
        return linkRelationMapper.batchUpdate(list);
    }
}
