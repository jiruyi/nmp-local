package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.TerminalDataDomainService;
import com.matrictime.network.dao.mapper.extend.NmplTerminalDataExtMapper;
import com.matrictime.network.modelVo.TerminalDataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/23.
 */
@Service
@Slf4j
public class TerminalDataDomainServiceImpl implements TerminalDataDomainService {

    @Resource
    private NmplTerminalDataExtMapper terminalDataExtMapper;

    @Override
    public List<TerminalDataVo> collectTerminalData() {
        List<TerminalDataVo> terminalDataVoList = terminalDataExtMapper.collectTerminalData();
        return terminalDataVoList;
    }
}
