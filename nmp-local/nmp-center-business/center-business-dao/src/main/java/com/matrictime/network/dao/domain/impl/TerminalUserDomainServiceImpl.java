package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.TerminalUserDomainService;
import com.matrictime.network.dao.mapper.NmplTerminalUserMapper;
import com.matrictime.network.dao.model.NmplTerminalUser;
import com.matrictime.network.dao.model.NmplTerminalUserExample;
import com.matrictime.network.modelVo.TerminalUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
@Service
public class TerminalUserDomainServiceImpl implements TerminalUserDomainService {

    @Resource
    private NmplTerminalUserMapper terminalUserMapper;

    @Override
    public int insertTerminalUser(TerminalUserVo terminalUserVo) {
        NmplTerminalUser terminalUser = new NmplTerminalUser();
        BeanUtils.copyProperties(terminalUserVo,terminalUser);
        return terminalUserMapper.insertSelective(terminalUser);

    }

    @Override
    public int updateTerminalUser(TerminalUserVo terminalUserVo) {
        NmplTerminalUserExample terminalUserExample = new NmplTerminalUserExample();
        NmplTerminalUserExample.Criteria criteria = terminalUserExample.createCriteria();
        if(!StringUtils.isEmpty(terminalUserVo.getTerminalStatus())){
            criteria.andTerminalStatusEqualTo(terminalUserVo.getTerminalStatus());
        }
        if(!StringUtils.isEmpty(terminalUserVo.getUserType())){
            criteria.andUserTypeEqualTo(terminalUserVo.getUserType());
        }
        criteria.andCompanyNetworkIdEqualTo(terminalUserVo.getCompanyNetworkId());
        NmplTerminalUser terminalUser = new NmplTerminalUser();
        BeanUtils.copyProperties(terminalUserVo,terminalUser);
        return terminalUserMapper.updateByExampleSelective(terminalUser,terminalUserExample);
    }

    @Override
    public List<NmplTerminalUser> selectTerminalUser(TerminalUserVo terminalUserVo) {
        NmplTerminalUserExample terminalUserExample = new NmplTerminalUserExample();
        NmplTerminalUserExample.Criteria criteria = terminalUserExample.createCriteria();
        if(!StringUtils.isEmpty(terminalUserVo.getTerminalStatus())){
            criteria.andTerminalStatusEqualTo(terminalUserVo.getTerminalStatus());
        }
        if(!StringUtils.isEmpty(terminalUserVo.getUserType())){
            criteria.andUserTypeEqualTo(terminalUserVo.getUserType());
        }
        criteria.andCompanyNetworkIdEqualTo(terminalUserVo.getCompanyNetworkId());
        return terminalUserMapper.selectByExample(terminalUserExample);
    }
}
