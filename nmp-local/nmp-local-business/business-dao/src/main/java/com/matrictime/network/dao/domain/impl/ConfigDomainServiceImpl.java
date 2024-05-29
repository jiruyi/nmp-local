package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.ConfigDomainService;
import com.matrictime.network.dao.mapper.extend.NmplConfigExtMapper;
import com.matrictime.network.modelVo.ConfigVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class ConfigDomainServiceImpl implements ConfigDomainService {

    @Resource
    NmplConfigExtMapper configExtMapper;

    @Override
    @Transactional
    public Integer update(List<ConfigVo> vos) {
        int i = configExtMapper.batchUpdate(vos);
        return i;
    }
}
