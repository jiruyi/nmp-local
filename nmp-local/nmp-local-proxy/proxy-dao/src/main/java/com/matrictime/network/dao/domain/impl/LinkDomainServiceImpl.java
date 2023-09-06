package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.LinkDomainService;
import com.matrictime.network.dao.mapper.extend.LinkMapperExt;
import com.matrictime.network.modelVo.LinkVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class LinkDomainServiceImpl implements LinkDomainService {

    @Resource
    private LinkMapperExt linkMapperExt;

    @Override
    @Transactional
    public int insertLink(List<LinkVo> list) {
        return linkMapperExt.batchInsert(list);
    }

    @Override
    @Transactional
    public int updateLink(List<LinkVo> list) {
        return linkMapperExt.batchUpdate(list);
    }
}
